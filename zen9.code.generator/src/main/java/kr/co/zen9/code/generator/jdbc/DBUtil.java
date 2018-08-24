package kr.co.zen9.code.generator.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	private DBUtil() {
		throw new IllegalStateException("DBUtil class");
	}
	
	public static void close(Connection ...connections){
	    if(connections != null ){
	        for(Connection conn : connections){
	            if(conn != null){
	                try {
	                    conn.close();
	                } catch (SQLException ignored) {
	                    //ignored
	                }
	            }
	        }
	    }
	}

	public static void close(ResultSet ...resultSets){
	    if(resultSets != null ){
	        for(ResultSet rs: resultSets){
	            if(rs != null){
	                try {
	                    rs.close();
	                } catch (SQLException ignored) {
	                    //ignored
	                }
	            }
	        }
	    }
	}

	public static void close(Statement ...statements){
	    if(statements != null){
	        for(Statement statement : statements){
	            if(statement != null){
	                try {
	                    statement.close();
	                } catch (SQLException ignored) {
	                    //ignored
	                }
	            }
	        }
	    }
	}
}
