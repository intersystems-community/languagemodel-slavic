/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl;

import static java.util.Arrays.stream;
import static java.util.Collections.emptyMap;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.xml.parsers.ParserConfigurationException;

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;
import org.languagetool.language.Ukrainian;
import org.xml.sax.SAXException;

import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalysisResult;
import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalyzer;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.RussianTagParser;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.TagParseResult;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.TagParser;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.UkrainianTagParser;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.WordFilter;

/**
 * Morphological analyzer which uses the <a href =
 * "https://languagetool.org/">LanguageTool</a> engine.
 *
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class LanguageToolAnalyzer implements MorphologicalAnalyzer {
	private static final String SENTENCE_END = "SENT_END";

	/**
	 * Single-character tokens which may be returned by the analyzer,
	 * but shouldn't be treated as words.
	 */
	private static final String WORD_DELIMITERS = "!\"'()*,-./:;<>?[]^`{}";

	private static final String UKRAINIAN_ONLY_EXPRESSIONS[] = {
		"\\b[\\p{L}]+\\'[\\p{L}]+\\-[\\p{L}]+\\'[\\p{L}]+\\b", // "f'oo-b'ar". This expression should be applied first.
		"\\b[\\p{L}]+\\-[\\p{L}]+\\'[\\p{L}]+\\b", // "foo-b'ar"
		"\\b[\\p{L}]+\\'[\\p{L}]+(\\-[\\p{L}]+[\\p{L}]+)?\\b", // "f'oo" (e.g.: "п'ятниця"), "f'oo-bar"
	};

	/**
	 * {@linkplain java.util.Arrays#spliterator(Object[], int, int)} returns an
	 * ordered spliterator, so we're safe here.
	 */
	static final WordFilter RUSSIAN_WORD_FILTER = text -> stream(UKRAINIAN_ONLY_EXPRESSIONS).reduce(text, (input, regex) -> input.replaceAll(regex, ""));

	final Map<String, Engine> analyzers = new LinkedHashMap<>();

	/**
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public LanguageToolAnalyzer() throws IOException, ParserConfigurationException, SAXException {
		this.analyzers.put("ru", Engine.create(new JLanguageTool(new Russian()), new RussianTagParser(), RUSSIAN_WORD_FILTER));
		this.analyzers.put("uk", Engine.create(new JLanguageTool(new Ukrainian()), new UkrainianTagParser()));
		for (final Engine engine : this.analyzers.values()) {
			final JLanguageTool languageTool = engine.languageTool;
			languageTool.activateDefaultFalseFriendRules();
			languageTool.activateDefaultPatternRules();
		}
	}

	/**
	 * @see MorphologicalAnalyzer#analyze(String)
	 */
	@Override
	public Map<String, Set<MorphologicalAnalysisResult>> analyze(final String text) {
		if (text == null || text.length() == 0) {
			return emptyMap();
		}

		final Map<String, Set<MorphologicalAnalysisResult>> results = new LinkedHashMap<>();

		this.analyzers.forEach((language, engine) -> {
			try {
				final AnalyzedSentence analyzedSentence = engine.languageTool.getAnalyzedSentence(engine.filter.getFilteredText(text));

				stream(analyzedSentence.getTokensWithoutWhitespace()).filter(analyzedTokenReadings -> {
					final String token = analyzedTokenReadings.getToken();
					/*
					 * Don't return single-char punctuation marks.
					 */
					return !token.isEmpty() && !(token.length() == 1 && WORD_DELIMITERS.contains(token));
				}).forEach(analyzedTokenReadings -> {
					final String token = analyzedTokenReadings.getToken();
					Set<MorphologicalAnalysisResult> resultsGroup = results.get(token);

					final List<AnalyzedToken> readings = analyzedTokenReadings.getReadings();
					if (readings.size() == 1 && readings.iterator().next().getLemma() == null) {
						if (resultsGroup == null) {
							/*
							 * Make sure tokens not present in the dictionary
							 * still appear in the results returned.
							 */
							results.put(token, Collections.<MorphologicalAnalysisResult>emptySet());
						}
					} else {
						for (final AnalyzedToken reading : readings) {
							final String posTag = reading.getPOSTag();
							/*
							 * Skip sentence end marks.
							 */
							if (!posTag.equals(SENTENCE_END)) {
								final TagParseResult tagParseResult = engine.tagParser.parse(posTag);
								final MorphologicalAnalysisResult result = new MorphologicalAnalysisResult(language, reading.getLemma(), tagParseResult.getPartOfSpeech(), tagParseResult.getCategories());
								if (resultsGroup == null || resultsGroup.isEmpty()) {
									resultsGroup = new LinkedHashSet<>();
									results.put(token, resultsGroup);
								}
								resultsGroup.add(result);
							}
						}
					}
				});
			} catch (final IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
		});
		return results;
	}

	/**
	 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
	 */
	private static final class Engine {
		@Nonnull
		final JLanguageTool languageTool;

		@Nonnull
		final TagParser tagParser;

		@Nonnull
		final WordFilter filter;

		/**
		 * @param languageTool
		 * @param tagParser
		 * @param filter
		 */
		private Engine(@Nonnull final JLanguageTool languageTool,
				@Nonnull final TagParser tagParser,
				@Nonnull final WordFilter filter) {
			this.languageTool = languageTool;
			this.tagParser = tagParser;
			this.filter = filter;
		}

		/**
		 * @param languageTool
		 * @param tagParser
		 */
		static Engine create(@Nonnull final JLanguageTool languageTool,
				@Nonnull final TagParser tagParser) {
			return new Engine(languageTool, tagParser, text -> text);
		}

		/**
		 * @param languageTool
		 * @param tagParser
		 * @param filter
		 */
		static Engine create(@Nonnull final JLanguageTool languageTool,
				@Nonnull final TagParser tagParser,
				@Nonnull final WordFilter filter) {
			return new Engine(languageTool, tagParser, filter);
		}
	}
}
