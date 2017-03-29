package com.capgemini.chess.exception;

public class PlayerValidationException extends ChessException {
	private static final long serialVersionUID = 1L;

	public PlayerValidationException(String message) {
		super(message);
	}
}
