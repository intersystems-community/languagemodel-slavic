/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl;

import static com.intersystems.iknow.languagemodel.slavic.impl.LanguageToolAnalyzer.RUSSIAN_WORD_FILTER;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalysisResult;
import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalyzer;
import com.intersystems.iknow.languagemodel.slavic.SerializingMorphologicalAnalyzer;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class LanguageToolAnalyzerTest {
	@SuppressWarnings("static-method")
	@Test
	public void testWordFilter() {
		assertEquals("він   вона", RUSSIAN_WORD_FILTER.getFilteredText("він п'ятниця п'ятниця вона"));
		assertEquals("він  вона", RUSSIAN_WORD_FILTER.getFilteredText("він п'ятниця вона"));
		assertEquals("він ", RUSSIAN_WORD_FILTER.getFilteredText("він п'ятниця"));
		assertEquals(" вона", RUSSIAN_WORD_FILTER.getFilteredText("п'ятниця вона"));
		assertEquals("", RUSSIAN_WORD_FILTER.getFilteredText("п'ятниця"));
		assertEquals("", RUSSIAN_WORD_FILTER.getFilteredText("П'ЯТНИЦЯ"));

		assertEquals("foo-bar", RUSSIAN_WORD_FILTER.getFilteredText("foo-bar"));
		assertEquals("", RUSSIAN_WORD_FILTER.getFilteredText("foo-b'ar"));
		assertEquals("", RUSSIAN_WORD_FILTER.getFilteredText("f'oo-bar"));
		assertEquals("", RUSSIAN_WORD_FILTER.getFilteredText("f'oo-b'ar"));
	}

	/**
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testApostrophe() throws IOException, ParserConfigurationException, SAXException {
		final MorphologicalAnalyzer analyzer = new LanguageToolAnalyzer();
		stream(new String[]{
				"п'ятниця",
		}).forEach(text -> {
			try {
				analyzer.analyze(text).forEach((word, results) -> {
					/*
					 * Make sure incorrectly split word
					 * fragments don't appear in the results.
					 */
					assertFalse(word.equals("п"));
					assertFalse(word.equals("ятниця"));

					/*
					 * Make sure no empty set of results is
					 * mapped to a word.
					 */
					assertFalse(results.isEmpty());

					/*
					 * Make sure all results have Ukrainian language set
					 * (Russian doesn't use apostrophe as a word part).
					 */
					assertTrue(results.stream().map(MorphologicalAnalysisResult::getLanguage).allMatch(language -> language.equals("uk")));
				});
			} catch (final RemoteException re) {
				re.printStackTrace();
				fail(re.getMessage());
			}
		});
	}

	/**
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	@SuppressWarnings("static-method")
	@Test
	public void test() throws IOException, ParserConfigurationException, SAXException {
		final SerializingMorphologicalAnalyzer analyzer = new SerializingMorphologicalAnalyzer(new LanguageToolAnalyzer());
		stream(new String[]{
				"какая",
				"Мама мыла раму.",
				"Варкалось, хливкие шорьки пырялись по наве.",
				"толстый и красивый",
				"по",
				"друзья",
				"люди",
				"Садок вишневий коло хати.",

				/*
				 * IV-th declension, should be the same for both Russian and Ukrainian.
				 */
				"дитя",
				"дитяти",

				"жовтогарячий",
				"побачений, висловлений, загорнений, розколений, позичений",
				"атакуючий, дозрілий",
		}).forEach(text -> {
			try {
				out.println(analyzer.analyze(text));
			} catch (final RemoteException re) {
				re.printStackTrace();
				fail(re.getMessage());
			}
		});
	}
}
