package kr.co.zen9.code.generator.make;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.co.zen9.code.generator.common.Const;
import kr.co.zen9.code.generator.common.Log;
import kr.co.zen9.code.generator.util.UtilsText;

public class PreparedSql {
	
	
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
			case "tinyint"				: jdbc = "TINYINT";				break;
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

	public static String select(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append("SELECT \n");
		if( columns != null ) {
			
			int i = 0;
			for (Map<String, String> column : columns) {
				
				String columnName = column.get("COLUMN_NAME").toLowerCase();
				if(i == 0) {
					sb.append("\t\t\t ");
					sb.append(columnName);
				}else {
					sb.append(", ").append(columnName);
				}
				
				i++;
			}
		}
		
		sb.append("\n");
		sb.append("\t\t");
		sb.append("FROM \n");
		sb.append("\t\t\t");
		sb.append(tableName).append("\n");
				
		whereSql(sb, pkColumns,columnNodeList);	

		
		return sb.toString();
	}
	
	public static String insert(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(UtilsText.concat("INSERT INTO ",tableName,"\n") );
		if( columns != null ) {
			
			int i = 0;
			for (Map<String, String> column : columns) {
				
				String orgColumnName = column.get("COLUMN_NAME").toLowerCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				
				if(i == 0) {
					sb.append("\t\t\t ( ");
					sb.append(orgColumnName);
				}else {
					sb.append(", ").append(orgColumnName);
				}
				
				i++;
			}
			sb.append(" ) \n");

		}
		
		sb.append("\n");
		sb.append("\t\t");
		sb.append("VALUES \n");
		sb.append("\t\t\t ");
		sb.append("( ");
		
		if( columns != null ) {
			
			int i = 0;
			for (Map<String, String> column : columns) {
				
				String orgColumnName = column.get("COLUMN_NAME").toLowerCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String dataType = column.get("DATA_TYPE");
				
				if(i > 0) {
					sb.append(", ");
				}

				sb.append(orgColumnName).append(" = ").append(UtilsText.concat("#{",columnName,", jdbcType=",jdbcType(dataType),"}"));

				
				i++;
			}
		}	
		
		sb.append(" ) ");

		
		return sb.toString();
	}
	
	public static String update(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(UtilsText.concat("UPDATE ",tableName,"\n") );
		sb.append("\t\t");
		sb.append("<set> \n");
		
		if( columns != null ) {
			
			int i = 0;
			for (Map<String, String> column : columns) {
				
				String orgColumnName = column.get("COLUMN_NAME").toLowerCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String dataType = column.get("DATA_TYPE");

				sb.append("\t\t\t").append(UtilsText.concat("<if test=\"",columnName," != null\"> \n"));
				sb.append("\t\t\t\t").append(orgColumnName).append(" = ").append(UtilsText.concat("#{",columnName,", jdbcType=",jdbcType(dataType),"}, \n"));
				sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));

				
				i++;
			}
		}	
		sb.append("\t\t");
		sb.append("</set> \n");

		whereSql(sb, pkColumns,columnNodeList);		
				
		return sb.toString();
	}
	
	public static String delete(String tableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(UtilsText.concat("DELETE FROM ",tableName,"\n") );

		whereSql(sb, pkColumns,columnNodeList);	
		
		return sb.toString();
	}
	
	private static void whereSql(StringBuilder sb, List<Map<String, String>> pkColumns, NodeList columnNodeList) {	
		
		if( pkColumns != null && pkColumns.size() > 0) {

			sb.append("\t\t");
			sb.append("WHERE \n");
	
			int i = 0;
			for (Map<String, String> pkColumn : pkColumns) {
				
				String orgColumnName = pkColumn.get(Const.COLUMN_NAME).toLowerCase();
				
				if(i == 0) {
					sb.append("\t\t\t ");
				}else {
					sb.append(" AND ");
				}
				sb.append(orgColumnName).append(" = ").append(generatorColumn(pkColumn, columnNodeList));
				
				i++;
			}
		}
	}
	
	
	private static String generatorColumn(Map<String, String> column, NodeList columnNodeList) {
		
		StringBuilder sb = new StringBuilder();
		
		String orgColumnName = column.get(Const.COLUMN_NAME).toLowerCase();
		String columnName = UtilsText.convert2CamelCase(orgColumnName);				
		String dataType = column.get(Const.DATA_TYPE);
		
		
		sb.append(UtilsText.concat("#{",columnName));
		if(columnNodeList != null && columnNodeList.getLength() > 0 ) {
			
			int columnNodeListLength = columnNodeList.getLength();
			
			for (int i = 0; i < columnNodeListLength; i++) {
				if(columnNodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) columnNodeList.item(i);
			  		
			  		String name  = element.getAttribute("name");
			  		String jdbcType  = element.getAttribute("jdbcType");
			  		String typeHandler  = element.getAttribute("typeHandler");
			  		
			  		if(!UtilsText.isBlank(name) && orgColumnName.equals(name.toLowerCase())) {
			  		
				  		if(!UtilsText.isBlank(jdbcType)) {
				  			sb.append(", jdbcType=").append(jdbcType);
				  		} else {
				  			sb.append(", jdbcType=").append(jdbcType(dataType));
				  		}
				  		
				  		if(!UtilsText.isBlank(typeHandler)) {
				  			sb.append(", typeHandler=").append(typeHandler);
				  		}
			  		}
			  		
				}
			}
		}else {
			sb.append(UtilsText.concat(", jdbcType=",jdbcType(dataType)));
		}
		
		sb.append("}");
		
		
		return sb.toString();
		
	}
	

}
