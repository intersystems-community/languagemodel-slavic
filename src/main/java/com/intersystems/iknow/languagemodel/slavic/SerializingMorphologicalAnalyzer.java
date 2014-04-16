/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import java.rmi.RemoteException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class SerializingMorphologicalAnalyzer {
	private static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(MorphologicalAnalysisResult.class, new MorphologicalAnalysisResultSerializer())
			.setPrettyPrinting()
			.create();

	private final MorphologicalAnalyzer delegate;

	/**
	 * @param delegate
	 */
	public SerializingMorphologicalAnalyzer(final MorphologicalAnalyzer delegate) {
		this.delegate = delegate;
	}

	/**
	 * @param text
	 * @throws AnalysisException
	 * @throws RemoteException
	 * @see MorphologicalAnalyzer#analyze(String)
	 */
	public String analyze(final String text)
	throws AnalysisException, RemoteException {
		return GSON.toJson(this.delegate.analyze(text));
	}
}
