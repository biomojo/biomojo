package org.biomojo.io;

import org.java0.core.exception.UncheckedException;

public class ParseException extends UncheckedException {

	private static final long serialVersionUID = 1L;

	public ParseException() {
		super();

	}

	public ParseException(final String message, final Throwable cause,
			final boolean enableSuppression, final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public ParseException(final String message, final Throwable cause) {
		super(message, cause);

	}

	public ParseException(final String message) {
		super(message);

	}

	public ParseException(final Throwable cause) {
		super(cause);

	}

}
