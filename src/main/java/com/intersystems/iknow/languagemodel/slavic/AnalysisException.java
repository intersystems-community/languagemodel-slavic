/*-
 * $Id$
 */
package com.intersystems.iknow.languagemodel.slavic;

import java.rmi.RemoteException;

/**
 * @author Andrey Shcheglov (mailto:andrey.shcheglov@intersystems.com)
 */
public final class AnalysisException extends RemoteException {
	private static final long serialVersionUID = 8302653720251053912L;

	/**
	 * @param cause
	 */
	public AnalysisException(final Throwable cause) {
		this.initCause(cause);
	}
}
