package kr.co.zen9.code.generator.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessSql {

	private static final String SQL_ORACLE_TABLE_COLUMN = "SELECT * FROM all_tab_columns WHERE TABLE_NAME= ? ";
	
	private static final String SQL_MSSQL_TABLE_COLUMN = "SELECT * FROM information_schema.columns where table_name = ? order by 5";

	private static final String SQL_MYSQL_TABLE_COLUMN = "SELECT column_name,data_type FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ORDINAL_POSITION";
	
	private static final String SQL_MARIA_TABLE_COLUMN = "SELECT column_name,data_type,column_key as key FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ORDINAL_POSITION";

	private DBConnection connection;

	private List<Map<String,String>> columns;
	
	private List<Map<String,String>> primaryColumns;
	
	public List<Map<String, String>> getColumns() {
		return columns;
	}

	public List<Map<String, String>> getPrimaryColumns() {
		return primaryColumns;
	}

	public ProcessSql(DBConnection connection) {
		this.connection = connection;
	}
	
	public void callOracleColumn() {
		
		clearColumns();
		
        try (
			PreparedStatement pstmt = connection.getConnection().prepareStatement(SQL_ORACLE_TABLE_COLUMN);
			ResultSet rs = pstmt.executeQuery();
		) {
                    	
        	setColumnsResultSet(rs,"PRI");

            
        } catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void callMssqlColumn() {
		
		clearColumns();

        try (
			PreparedStatement pstmt = connection.getConnection().prepareStatement(SQL_MSSQL_TABLE_COLUMN);
			ResultSet rs = pstmt.executeQuery();
		) {            

        	setColumnsResultSet(rs,"PRI");
            
        } catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void callMysqlColumn() {
		
		clearColumns();

        try (
			PreparedStatement pstmt = connection.getConnection().prepareStatement(SQL_MYSQL_TABLE_COLUMN);
			ResultSet rs = pstmt.executeQuery();
		) {            

        	setColumnsResultSet(rs,"PRI");
            
        } catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void callMariaColumn() {
		
		clearColumns();

        try (
			PreparedStatement pstmt = connection.getConnection().prepareStatement(SQL_MARIA_TABLE_COLUMN);
			ResultSet rs = pstmt.executeQuery();
		) {            

        	setColumnsResultSet(rs,"PRI");
            
        } catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	private void setColumnsResultSet(ResultSet rs,String keyType) throws SQLException {
    	
    	List<Map<String,String>> tempColumns = Arrays.asList();
    	List<Map<String,String>> tempPrimaryColumns = Arrays.asList();
    	
        while(rs.next()) {
        	
        	String key = rs.getString("KEY");
        	
        	Map<String,String> column = new HashMap<>();
        	column.put("name", rs.getString("COLUMN_NAME"));
        	column.put("type", rs.getString("DATA_TYPE"));
        	
        	if(keyType.equals(key)) {
        		tempPrimaryColumns.add(column);
        	}
        	
        	tempColumns.add(column);
        }
        
        this.columns = tempColumns;
        this.primaryColumns = tempPrimaryColumns;
	}
	
	private void clearColumns() {
        this.columns = null;
        this.primaryColumns = null;;
	}
}

