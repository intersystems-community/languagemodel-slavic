/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic.impl.languagetool;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
@FunctionalInterface
public interface WordFilter {
	/**
	 * @param originalText
	 */
	String getFilteredText(final String originalText);
}
