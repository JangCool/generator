package kr.co.zen9.code.generator.common;

import java.io.PrintStream;

public class Log {

	private static final PrintStream out = System.out;
	private static final PrintStream err = System.err;
	
	public static void debug(String message) {
		out.println("DEBUG : ".concat(message));
	}
	
	public static void warning(String message) {
		out.println("WARNING : ".concat(message));
	}

	public static void info(String message) {
		out.println("INFO : ".concat(message));
	}
	
	public static void error(String message) {
		err.println("ERROR : ".concat(message));
	}
}
