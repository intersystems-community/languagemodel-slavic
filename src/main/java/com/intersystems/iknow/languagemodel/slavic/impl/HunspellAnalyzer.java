/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import com.atlascopco.hunspell.Hunspell;
import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalysisResult;
import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalyzer;

/**
 * Morphological analyzer which uses the <a href =
 * "http://hunspell.sourceforge.net">Hunspell</a> engine.
 *
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class HunspellAnalyzer implements MorphologicalAnalyzer {
	private static final String POSIX_SEARCH_PATHS[] = {
		"/usr/share/hunspell",
		"/usr/local/share/hunspell",
		getProperty("user.dir"),
	};

	private static final String DARWIN_SEARCH_PATHS[] = {
		"/System/Library/Spelling",
		"/Library/Spelling",
		getProperty("user.home") + File.separatorChar + "Spelling",
		"/opt/local/share/hunspell",
		"/sw/share/hunspell",
	};

	private static final String DICTIONARY_SUFFIX = ".dic";

	private static final String AFFIX_SUFFIX = ".aff";

	/**
	 * Single-character tokens which may be returned by the analyzer,
	 * but shouldn't be treated as words. Hyphen (-) and apostrophe (')
	 * are not included, as they may be legal word parts ("красно-белый",
	 * "п'ятниця").
	 */
	private static final String WORD_DELIMITERS = "!\"()*,./:;<>?[]^`{} \t\r\n";

	private static final Pattern WORD_DELIMITERS_PATTERN;

	static {
		final StringBuilder regex = new StringBuilder();
		regex.append('[');
		for (int i = 0, n = WORD_DELIMITERS.length(); i < n; i++) {
			regex.append('\\').append(WORD_DELIMITERS.charAt(i));
		}
		regex.append("]+");
		WORD_DELIMITERS_PATTERN = Pattern.compile(regex.toString());
	}

	private final LinkedHashMap<String, Set<Hunspell>> analyzers = new LinkedHashMap<>();

	/**
	 * @throws IOException
	 */
	public HunspellAnalyzer() throws IOException {
		final Set<String> basenames = new LinkedHashSet<>(asList(
				"ru_RU",
				"uk_UA"));

		final Set<String> searchPaths = new LinkedHashSet<>();
		for (final String searchPath : POSIX_SEARCH_PATHS) {
			final File file = new File(searchPath);
			if (file.exists()) {
				searchPaths.add(file.getCanonicalPath());
			}
		}

		if (getProperty("os.name").equals("Mac OS X")) {
			for (final String searchPath : DARWIN_SEARCH_PATHS) {
				final File file = new File(searchPath);
				if (file.exists()) {
					searchPaths.add(file.getCanonicalPath());
				}
			}
		}


		final Iterator<String> it = searchPaths.iterator();
		while (it.hasNext()) {
			if (!new File(it.next()).exists()) {
				it.remove();
			}
		}

		final LinkedHashMap<String, Set<String>> dictionaries = new LinkedHashMap<>();

		for (final String basename : basenames) {
			for (final String searchPath : searchPaths) {
				final File dictionary = new File(searchPath, basename + DICTIONARY_SUFFIX);
				final File affix = new File(dictionary.getCanonicalFile().getParent(), basename + AFFIX_SUFFIX);
				if (dictionary.exists() && affix.exists()) {
					final String language = basename.substring(0, 2);
					Set<String> dictionaryGroup = dictionaries.get(language);
					if (dictionaryGroup == null) {
						dictionaryGroup = new LinkedHashSet<>();
						dictionaries.put(language, dictionaryGroup);
					}
					final String dictionaryPath = dictionary.getCanonicalPath();
					dictionaryGroup.add(dictionaryPath.substring(0, dictionaryPath.length() - 4));
				}
			}
		}

		for (final Entry<String, Set<String>> dictionaryGroup : dictionaries.entrySet()) {
			final Set<Hunspell> engineGroup = new LinkedHashSet<>();
			for (final String dictionary : dictionaryGroup.getValue()) {
				engineGroup.add(new Hunspell(dictionary + DICTIONARY_SUFFIX, dictionary + AFFIX_SUFFIX));
			}
			this.analyzers.put(dictionaryGroup.getKey(), engineGroup);
		}
	}

	/**
	 * @see MorphologicalAnalyzer#analyze(String)
	 */
	@Override
	public Map<String, Set<MorphologicalAnalysisResult>> analyze(final String text) {
		final Map<String, Set<MorphologicalAnalysisResult>> results = new LinkedHashMap<>();

		for (final Entry<String, Set<Hunspell>> analyzerGroup : this.analyzers.entrySet()) {
			for (final Hunspell analyzer : analyzerGroup.getValue()) {
				for (final String token : split(text)) {
					Set<MorphologicalAnalysisResult> resultsGroup = results.get(token);

					final List<String> readings = analyzer.stem(token);
					if (readings.isEmpty()) {
						if (resultsGroup == null) {
							/*
							 * Make sure tokens not present in the dictionary
							 * still appear in the results returned.
							 */
							results.put(token, Collections.<MorphologicalAnalysisResult>emptySet());
						}
						continue;
					}
					for (final String reading : readings) {
						final MorphologicalAnalysisResult result = new MorphologicalAnalysisResult(analyzerGroup.getKey(), reading);
						if (resultsGroup == null || resultsGroup.isEmpty()) {
							resultsGroup = new LinkedHashSet<>();
							results.put(token, resultsGroup);
						}
						resultsGroup.add(result);
					}
				}
			}
		}
		return results;
	}

	/**
	 * Splits <em>text</em> into individual words.
	 *
	 * @param text
	 */
	private static Set<String> split(final String text) {
		final Set<String> words = new LinkedHashSet<>();
		words.addAll(asList(WORD_DELIMITERS_PATTERN.split(text)));
		return words;
	}
}
