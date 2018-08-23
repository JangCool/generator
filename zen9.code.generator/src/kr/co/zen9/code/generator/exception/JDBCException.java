package kr.co.zen9.code.generator.exception;

public class JDBCException extends RuntimeException {

	public JDBCException() {
		super();
	}
	
	public JDBCException(String message) {
		super(message);
	}
	
	public JDBCException(Throwable e) {
		super(e);
	}	

}
