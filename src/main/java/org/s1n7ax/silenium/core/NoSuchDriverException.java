package org.s1n7ax.silenium.core;

/**
 * NoSuchDriverException
 */
public class NoSuchDriverException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchDriverException() {
	}

	public NoSuchDriverException(final String message) {
		super(message);
	}

}