package kr.co.zen9.code.generator.make;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.zen9.code.generator.util.UtilsText;

public class PreparedDto {

	private static String javaType(String dataType) {
		
		String java = null;
		
		switch (dataType) {
			case "bigint"				: java = "long";						break;
			case "binary"				: java = "byte[]";						break;
			case "bit"	 				: java = "boolean";						break;
			case "char"	 				: java = "String";						break;
			case "date"					: java = "java.sql.Date";				break;
			case "datetime"				: java = "java.sql.Timestamp";			break;
			case "datetime2"			: java = "java.sql.Timestamp";			break;
			case "decimal"				: java = "java.math.BigDecimal";		break;
			case "float"				: java = "double";						break;
			case "image"				: java = "byte[]";						break;
			case "int"					: java = "int";							break;
			case "money"				: java = "java.math.BigDecimal";		break;
			case "nchar"				: java = "String";						break;
			case "ntext"				: java = "String";						break;
			case "numeric"				: java = "java.math.BigDecimal";		break;
			case "nvarchar"				: java = "String";						break;
			case "nvarchar(max)"		: java = "String";						break;
			case "real"					: java = "float";						break;
			case "smalldatetime"		: java = "java.sql.Timestamp";			break;
			case "smallint"				: java = "short";						break;
			case "smallmoney"			: java = "java.math.BigDecimal";		break;
			case "text"					: java = "String";						break;
			case "time"					: java = "java.sql.Time";				break;
			case "timestamp"			: java = "byte[]";						break;
			case "tinyint"				: java = "short";						break;
			case "udt"					: java = "byte[]";						break;
			case "uniqueidentifier"		: java = "String";						break;
			case "varbinary"			: java = "byte[]";						break;
			case "varbinary(max)"		: java = "byte[]";						break;
			case "varchar"				: java = "String";						break;
			case "varchar(max)"			: java = "String";						break;
			case "xml"					: java = "String";						break;

		}
		
		return java;
	}

	public static List<Map<String, String>> dto(String orgTableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		
		List<Map<String, String>> fieldList = new ArrayList<>();
		
		for (Map<String, String> column : columns) {
			
			String orgColumnName = column.get("COLUMN_NAME").toLowerCase();
			String field = UtilsText.convert2CamelCase(orgColumnName);				
			String field2 = UtilsText.convert2CamelCaseTable(orgColumnName);				
			String dataType = column.get("DATA_TYPE");
			
			Map<String,String> fieldMap = new HashMap<>();
			fieldMap.put("field", field);
			fieldMap.put("field2", field2);
			fieldMap.put("javaType", javaType(dataType));
			
			fieldList.add(fieldMap);
		}
		
		return fieldList;
	}
}
