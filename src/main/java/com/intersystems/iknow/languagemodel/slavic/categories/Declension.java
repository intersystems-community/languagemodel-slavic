/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.categories;

import com.intersystems.iknow.languagemodel.slavic.GrammaticalCategory;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public enum Declension implements GrammaticalCategory {
	FIRST,
	SECOND,
	/**
	 * Russian: also "путь" (which is formally heteroclitic).
	 */
	THIRD,
	/**
	 * <ul>
	 * <li>Russian: "дитя" and 11 special heteroclitic forms ("время",
	 * "бремя", "стремя", "племя", "пламя", "знамя", "темя", "семя",
	 * "имя", "вымя", "голомя")</li>
	 * <li>Ukrainian: "дитя" and all forms that have "-онк", "-онок",
	 * "-ёнк" suffixes and belong to the 2nd declension in Russian
	 * ("лоша", "теля", etc.)</li>
	 * </ul>
	 */
	FOURTH,
}
