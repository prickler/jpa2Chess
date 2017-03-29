package com.capgemini.chess.exception;

public abstract class ChessException extends Exception {
	private static final long serialVersionUID = 1L;

	public ChessException(String message) {
		super(message);
	}
}
