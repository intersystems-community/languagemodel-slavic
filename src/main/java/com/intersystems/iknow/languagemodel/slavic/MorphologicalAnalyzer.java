/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

/**
 * Common superinterface for all stemming engines.
 *
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public interface MorphologicalAnalyzer extends Remote {
	/**
	 * Returns a <em>map</em> of <em>tokens</em> (i.&nbsp;e. words) and
	 * (possibly, multiple) analyzes corresponding to each token. If
	 * a particular token can't be found in the dictionary, an empty
	 * set will be mapped to this token.
	 *
	 * @param text a single word or a word sequence (i.&nbsp;e. a sentence)
	 * @return a <em>map</em> of <em>tokens</em> and analyzes
	 *         corresponding to each token.
	 * @throws RemoteException
	 */
	Map<String, Set<MorphologicalAnalysisResult>> analyze(final String text)
	throws RemoteException;
}
