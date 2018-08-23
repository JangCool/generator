package kr.co.zen9.code.generator.process;

import java.util.List;
import java.util.Map;

import kr.co.zen9.code.generator.common.Log;
import kr.co.zen9.code.generator.util.UtilsText;

public class Sql {
	
	
	private static String jdbcType(String dataType) {
		
		String jdbc = null;
		
		switch (dataType) {
			case "bigint"				: jdbc = "BIGINT";				break;
			case "binary"				: jdbc = "BINARY";				break;
			case "bit"	 				: jdbc = "BIT";					break;
			case "char"	 				: jdbc = "CHAR";				break;
			case "date"					: jdbc = "DATE";				break;
			case "datetime"				: jdbc = "TIMESTAMP";			break;
			case "datetime2"			: jdbc = "TIMESTAMP";			break;
			case "decimal"				: jdbc = "DECIMAL";				break;
			case "double"				: jdbc = "DOUBLE";				break;
			case "image"				: jdbc = "LONGVARBINARY";		break;
			case "int"					: jdbc = "INTEGER";				break;
			case "money"				: jdbc = "DECIMAL";				break;
			case "nchar"				: jdbc = "NCHAR";				break;
			case "ntext"				: jdbc = "LONGNVARCHAR";		break;
			case "numeric"				: jdbc = "NUMERIC";				break;
			case "nvarchar"				: jdbc = "NVARCHAR";			break;
			case "nvarchar(max)"		: jdbc = "NVARCHAR";			break;
			case "real"					: jdbc = "REAL";				break;
			case "smalldatetime"		: jdbc = "TIMESTAMP";			break;
			case "smallint"				: jdbc = "SMALLINT";			break;
			case "smallmoney"			: jdbc = "DECIMAL";				break;
			case "text"					: jdbc = "LONGVARCHAR";			break;
			case "time"					: jdbc = "TIME";				break;
			case "timestamp"			: jdbc = "BINARY";				break;
			case "udt"					: jdbc = "VARBINARY";			break;
			case "uniqueidentifier"		: jdbc = "CHAR";				break;
			case "varbinary"			: jdbc = "VARBINARY";			break;
			case "varbinary(max)"		: jdbc = "VARBINARY";			break;
			case "varchar"				: jdbc = "VARCHAR";				break;
			case "varchar(max)"			: jdbc = "VARCHAR";				break;
			case "xml"					: jdbc = "LONGVARCHAR";			break;

		}
		
		return jdbc;
	}

	public static String select(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT \n");
		if( columns != null ) {
			
			int i = 0;
			for (Map<String, String> column : columns) {
				
				String columnName = column.get("COLUMN_NAME").toLowerCase();
				if(i == 0) {
					sb.append("\t ");
					sb.append(columnName);
				}else {
					sb.append(", ").append(columnName);
				}
				
				i++;
			}
		}
		
		sb.append("\n");
		sb.append("FROM \n");
		sb.append("\t ").append(tableName).append("\n");
				
		if( pkColumns != null && pkColumns.size() > 0) {

			sb.append("WHERE \n");
	
			int i = 0;
			for (Map<String, String> pkColumn : pkColumns) {
				
				String columnName = pkColumn.get("COLUMN_NAME").toLowerCase();
				String dataType = pkColumn.get("DATA_TYPE");
				Log.debug(columnName);
				Log.debug(dataType);
				if(i == 0) {
					sb.append("\t ");
					sb.append(columnName).append("=").append(UtilsText.concat("#{age,jdbcType=",jdbcType(dataType),"}"));
				}else {
					sb.append(columnName);
				}
				
				i++;
			}
		}
		
		Log.debug(sb.toString());
		
		return sb.toString();
	}
	
	public static String insert(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		return null;
	}
	
	public static String update(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		return null;
	}
	
	public static String delete(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		return null;
	}
	

}
