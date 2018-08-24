package kr.co.zen9.code.generator.jdbc;

import org.w3c.dom.Element;

import kr.co.zen9.code.generator.exception.JDBCException;
import kr.co.zen9.code.generator.parser.XmlParser;

public class DBInfo {

	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static final String MARIA_DRIVER = "org.mariadb.jdbc.Driver";
	
	
	private String driver;
	private String url;
	private String username;
	private String password;
	
	
	public DBInfo(XmlParser xp) throws Exception {
		
		Element jdbcElement = (Element) xp.getDoc().getElementsByTagName("jdbc").item(0);
		
		this.url = jdbcElement.getAttribute("url");
		this.driver = jdbcElement.getAttribute("driverClass");
		this.username = jdbcElement.getAttribute("username");
		this.password = jdbcElement.getAttribute("password");
		
		validate();
	}

	
	private void validate() throws Exception{
		
		if(this.url == null || "".equals(this.url) ) {
			throw new JDBCException("jdbc url 값이 존재하지 않습니다.");
		}else if (this.driver == null || "".equals(this.driver) ) {
			throw new JDBCException("jdbc driverClass 값이 존재하지 않습니다.");
		}else if (this.driver == null || "".equals(this.driver) ) {
			throw new JDBCException("jdbc username 값이 존재하지 않습니다.");
		}else if (this.driver == null || "".equals(this.driver) ) {
			throw new JDBCException("jdbc username 값이 존재하지 않습니다.");
		}
	}
	
	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isOracle() {
		return (ORACLE_DRIVER.equals(driver));
	}

	public boolean isMssql() {
		return (MSSQL_DRIVER.equals(driver));
	}

	public boolean isMysql() {
		return (MYSQL_DRIVER.equals(driver));
	}
	
	public boolean isMaria() {
		return (MARIA_DRIVER.equals(driver));
	}
	
	
}
