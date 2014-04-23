/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl;

import static java.util.Collections.emptyMap;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Russian;
import org.languagetool.language.Ukrainian;
import org.xml.sax.SAXException;

import com.intersystems.iknow.languagemodel.slavic.AnalysisException;
import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalysisResult;
import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalyzer;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.RussianTagParser;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.TagParseResult;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.TagParser;
import com.intersystems.iknow.languagemodel.slavic.impl.languagetool.UkrainianTagParser;

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

	final Map<String, Engine> analyzers = new HashMap<>();

	/**
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public LanguageToolAnalyzer() throws IOException, ParserConfigurationException, SAXException {
		this.analyzers.put("ru", Engine.create(new JLanguageTool(new Russian()), new RussianTagParser()));
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
	public Map<String, Set<MorphologicalAnalysisResult>> analyze(final String text) throws AnalysisException {
		if (text == null || text.length() == 0) {
			return emptyMap();
		}

		try {
			final Map<String, Set<MorphologicalAnalysisResult>> results = new LinkedHashMap<>();

			for (final Entry<String, Engine> analyzer : this.analyzers.entrySet()) {
				final AnalyzedSentence analyzedSentence = analyzer.getValue().languageTool.getAnalyzedSentence(text);

				for (final AnalyzedTokenReadings analyzedTokenReadings : analyzedSentence.getTokensWithoutWhitespace()) {
					final String token = analyzedTokenReadings.getToken();
					if (token.isEmpty() || token.length() == 1 && WORD_DELIMITERS.contains(token)) {
						/*
						 * Don't return single-char punctuation marks.
						 */
						continue;
					}

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
						continue;
					}
					for (final AnalyzedToken reading : readings) {
						final String posTag = reading.getPOSTag();
						if (posTag.equals(SENTENCE_END)) {
							/*
							 * Skip sentence end marks.
							 */
							continue;
						}
						final TagParseResult tagParseResult = analyzer.getValue().tagParser.parse(posTag);
						final MorphologicalAnalysisResult result = new MorphologicalAnalysisResult(analyzer.getKey(), reading.getLemma(), tagParseResult.getPartOfSpeech(), tagParseResult.getCategories());
						if (resultsGroup == null || resultsGroup.isEmpty()) {
							resultsGroup = new LinkedHashSet<>();
							results.put(token, resultsGroup);
						}
						resultsGroup.add(result);
					}
				}
			}
			return results;
		} catch (final IOException ioe) {
			throw new AnalysisException(ioe);
		}
	}

	/**
	 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
	 */
	private static final class Engine {
		final JLanguageTool languageTool;

		final TagParser tagParser;

		/**
		 * @param languageTool
		 * @param tagParser
		 */
		private Engine(final JLanguageTool languageTool, final TagParser tagParser) {
			this.languageTool = languageTool;
			this.tagParser = tagParser;
		}

		/**
		 * @param languageTool
		 * @param tagParser
		 */
		static Engine create(final JLanguageTool languageTool, final TagParser tagParser) {
			return new Engine(languageTool, tagParser);
		}
	}
}
