package io.constructor.client;

public class ConstructorException extends Exception {
	private Integer errorCode;

	public ConstructorException(String msg) {
		super(msg);
	}

	public ConstructorException(Exception e) {
		super(e);
		if(e instanceof ConstructorException) {
			Integer errorCode = ((ConstructorException) e).getErrorCode();
			if(errorCode != null){
				this.errorCode = errorCode;
			}
		}
	}

	public ConstructorException(String msg, Integer code) {
		super(msg);
		this.errorCode = code;
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}
}
