/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl;

import java.io.IOException;

import org.junit.Test;

import com.intersystems.iknow.languagemodel.slavic.MorphologicalAnalyzer;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class HunspellAnalyzerTest {
	@SuppressWarnings("static-method")
	@Test
	public void test() throws IOException {
		final MorphologicalAnalyzer analyzer = new HunspellAnalyzer();

		System.out.println(analyzer.analyze("какая"));
		System.out.println(analyzer.analyze("Мама мыла раму."));
		System.out.println(analyzer.analyze("Варкалось, хливкие шорьки пырялись по наве."));
		System.out.println(analyzer.analyze("толстый и красивый"));
		System.out.println(analyzer.analyze("по"));
		System.out.println(analyzer.analyze("друзья"));
		System.out.println(analyzer.analyze("люди"));
		System.out.println(analyzer.analyze("Садок вишневий коло хати."));

		/*
		 * IV-th declension, should be the same for both Russian and Ukrainian.
		 */
		System.out.println(analyzer.analyze("дитя"));
		System.out.println(analyzer.analyze("дитяти"));

		System.out.println(analyzer.analyze("жовтогарячий"));
		System.out.println(analyzer.analyze("п'ятниця"));
		System.out.println(analyzer.analyze("побачений, висловлений, загорнений, розколений, позичений"));
		System.out.println(analyzer.analyze("атакуючий, дозрілий"));
	}
}
