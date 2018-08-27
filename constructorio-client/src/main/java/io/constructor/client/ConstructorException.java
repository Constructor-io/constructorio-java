package io.constructor.client;

class ConstructorException extends Exception {
	public ConstructorException(String msg) {
		super(msg);
	}

	public ConstructorException(Exception e) {
		super(e);
	}
}
