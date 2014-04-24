/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl.languagetool;

import static java.lang.System.out;
import static java.util.Arrays.stream;
import static junit.framework.Assert.fail;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.intersystems.iknow.languagemodel.slavic.SerializingMorphologicalAnalyzer;
import com.intersystems.iknow.languagemodel.slavic.impl.LanguageToolAnalyzer;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class LanguageToolAnalyzerTest {
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
				"п'ятниця",
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
