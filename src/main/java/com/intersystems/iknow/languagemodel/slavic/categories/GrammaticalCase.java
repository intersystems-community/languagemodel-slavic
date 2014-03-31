/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.categories;

import com.intersystems.iknow.languagemodel.slavic.GrammaticalCategory;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public enum GrammaticalCase implements GrammaticalCategory {
	/**
	 * Russian: именительный
	 */
	NOMINATIVE,
	/**
	 * Russian: родительный
	 */
	GENITIVE,
	/**
	 * Russian: дательный
	 */
	DATIVE,
	/**
	 * Russian: винительный
	 */
	ACCUSATIVE,
	/**
	 * Russian: творительный
	 */
	INSTRUMENTAL,
	/**
	 * Russian: предложный
	 */
	PREPOSITIONAL,
	/**
	 * Russian: звательный
	 */
	VOCATIVE,
}
