package br.com.testePratico.exception;

public class NotFound extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFound(final String message) {
		this(message, null);
	}

	public NotFound(final String message, final Throwable cause) {
		super(message, cause);
	}
}
