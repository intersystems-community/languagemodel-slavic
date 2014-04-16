/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl.languagetool;

import com.intersystems.iknow.languagemodel.slavic.GrammaticalCategory;

/**
 * Maps a set of implementation-specific tags onto {@linkplain
 * GrammaticalCategory grammatical categories}.
 *
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public interface TagParser {
	/**
	 * @param tags
	 */
	TagParseResult parse(final String tags);
}
