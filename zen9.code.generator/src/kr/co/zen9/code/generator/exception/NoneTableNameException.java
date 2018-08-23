package kr.co.zen9.code.generator.exception;

public class NoneTableNameException extends RuntimeException {
	
	public NoneTableNameException() {
		super();
	}
	
	public NoneTableNameException(String message) {
		super(message);
	}
	
	public NoneTableNameException(Throwable e) {
		super(e);
	}	

}
