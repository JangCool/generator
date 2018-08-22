package kr.co.zen9.code.generator.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import kr.co.zen9.code.generator.common.Log;

public class DBConnection {

    private Connection connection;

    public DBConnection(DBInfo dbInfo) throws SQLException {
    	
        try {
            Class.forName(dbInfo.driver);
            this.connection = DriverManager.getConnection(dbInfo.url, dbInfo.username, dbInfo.password);
        } catch (ClassNotFoundException ex) {
        	Log.error("Database Connection Creation Failed : " + ex.getMessage());
        }
    }
    

    public Connection getConnection() {
        return connection;
    }
    
    public void close() {
    	
        if (connection == null) {
        	Log.warning("No connection found");
        } else {
            try {
                connection.close();
                connection = null;
            }
            catch (SQLException e) {
            	Log.error("Failed to close the connection"+ e);
            }
        }
    }
}
