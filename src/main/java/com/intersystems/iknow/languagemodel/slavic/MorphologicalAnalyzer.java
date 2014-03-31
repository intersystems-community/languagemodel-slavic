/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * Common superinterface for all stemmin engines.
 *
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public interface MorphologicalAnalyzer extends Remote {
	/**
	 * @param word
	 * @return an empty set if word can't be found in the dictionary.
	 * @throws RemoteException
	 */
	Set<MorphologicalAnalysisResult> analyze(final String word)
	throws RemoteException;
}
