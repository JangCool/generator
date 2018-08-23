package kr.co.zen9.code.generator.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.zen9.code.generator.common.Log;

public class ProcessSql {

	private static final String SQL_ORACLE_TABLE_COLUMN = "SELECT * FROM all_tab_columns WHERE TABLE_NAME= ? ";
	
	private static final String SQL_MSSQL_TABLE_COLUMN = "SELECT column_name,data_type,ordinal_position FROM information_schema.columns where table_name = ? order by ordinal_position";
	
	private static final String SQL_MSSQL_TABLE_PK_COLUMN = "SELECT Col.Column_Name, (SELECT data_type FROM information_schema.columns dtcn WHERE dtcn.table_name=col.table_name AND dtcn.column_name=col.column_name) as data_type FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS Tab,INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE Col WHERE Col.Constraint_Name = Tab.Constraint_Name AND Col.Table_Name = Tab.Table_Name AND Constraint_Type = 'PRIMARY KEY' AND Col.Table_Name = ?";

	private static final String SQL_MYSQL_TABLE_COLUMN = "SELECT column_name,data_type FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ORDINAL_POSITION";
	
	private static final String SQL_MARIA_TABLE_COLUMN = "SELECT column_name,data_type,column_key as key FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ORDINAL_POSITION";

	private DBConnection connection;

	private List<Map<String,String>> columns;
	
	private List<Map<String,String>> primaryColumns;
	
	private DBInfo dbInfo;
	
	public DBInfo getDbInfo() {
		return dbInfo;
	}

	public List<Map<String, String>> getColumns() {
		return columns;
	}

	public List<Map<String, String>> getPrimaryColumns() {
		return primaryColumns;
	}

	public ProcessSql(DBConnection connection) {
		this.connection = connection;
		this.dbInfo = connection.getDbInfo();
	}
	
	public void callOracleColumn(String tableName) {
		
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
	
	public void callMssqlColumn(String tableName) {
		
		clearColumns();
		PreparedStatement pstmt = null;
		PreparedStatement pkPstmt = null;
		ResultSet rs = null;
		ResultSet pkRs = null;
        try {
        	
        	pstmt = connection.getConnection().prepareStatement(SQL_MSSQL_TABLE_COLUMN);
        	pstmt.setString(1, tableName);

        	rs = pstmt.executeQuery();
        	
        	setColumnsResultSet(rs);
        	
        	pkPstmt = connection.getConnection().prepareStatement(SQL_MSSQL_TABLE_PK_COLUMN);
        	pkPstmt.setString(1, tableName);

        	pkRs = pkPstmt.executeQuery();
        	
        	setPKColumnsResultSet(pkRs);
            
        } catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.close(pkRs);
			DBUtil.close(pkPstmt);	
			DBUtil.close(rs);
			DBUtil.close(pstmt);			
		}
	}
	
	public void callMysqlColumn(String tableName) {
		
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
	
	private void setColumnsResultSet(ResultSet rs) throws SQLException {
		setColumnsResultSet(rs, null);
	}
	
	private void setColumnsResultSet(ResultSet rs,String keyType) throws SQLException {
    	
    	List<Map<String,String>> tempColumns = new ArrayList<>();
    	List<Map<String,String>> tempPrimaryColumns = new ArrayList<>();
    	
        while(rs.next()) {
        	
        	Log.debug("column name : " + rs.getString("COLUMN_NAME").concat(", type : ").concat(rs.getString("DATA_TYPE")));
        	
        	Map<String,String> column = new HashMap<>();
 
        	column.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
        	column.put("DATA_TYPE  ", rs.getString("DATA_TYPE"));
        	
        	if(keyType != null) {

        		String key = rs.getString("KEY");
        		if(keyType.equals(key)) {
            		tempPrimaryColumns.add(column);        			
        		}

        	}
        	
        	tempColumns.add(column);
        }
        
        this.columns = tempColumns;
        this.primaryColumns = tempPrimaryColumns;
	}
	
	
	private void setPKColumnsResultSet(ResultSet rs) throws SQLException {
    	
    	List<Map<String,String>> tempPrimaryColumns = new ArrayList<>();
    	
        while(rs.next()) {     	
        	Map<String,String> column = new HashMap<>();
        	Log.debug("PK column name : " + rs.getString("COLUMN_NAME").concat(", type : ").concat(rs.getString("DATA_TYPE")));

        	column.put("COLUMN_NAME", rs.getString("COLUMN_NAME"));
        	column.put("DATA_TYPE", rs.getString("DATA_TYPE"));
        	
        	tempPrimaryColumns.add(column);
        }
        
        this.primaryColumns = tempPrimaryColumns;
	}
	
	
	private void clearColumns() {
        this.columns = null;
        this.primaryColumns = null;;
	}
}

