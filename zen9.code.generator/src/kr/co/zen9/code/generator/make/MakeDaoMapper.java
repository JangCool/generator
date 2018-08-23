package kr.co.zen9.code.generator.make;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import freemarker.template.Template;
import kr.co.zen9.code.generator.common.Log;
import kr.co.zen9.code.generator.exception.NoneTableNameException;
import kr.co.zen9.code.generator.jdbc.DBInfo;
import kr.co.zen9.code.generator.jdbc.ProcessSql;
import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsDate;
import kr.co.zen9.code.generator.util.UtilsText;

public class MakeDaoMapper extends BaseMake{

	private ProcessSql processSql;
	private XmlParser xmlParser;
	
	public MakeDaoMapper() {
		super();
	}
	
	public MakeDaoMapper(XmlParser xmlParser, ProcessSql processSql) {
		this.processSql = processSql;
		this.xmlParser = xmlParser;
	}

	@Override
	public void generator() throws Exception {
				
		NodeList tables = xmlParser.getDoc().getElementsByTagName("tables");
		
   		int tablesLength = tables.getLength();
		
		for (int i = 0; i < tablesLength; i++) {
			
			Node nodeTables = tables.item(i);
			Element elementTables = (Element) nodeTables;
			
			NodeList childTables = elementTables.getElementsByTagName("table");						
	   		int childTablesLength = childTables.getLength();
			
	   		String target = getPropertyKey(elementTables.getAttribute("target"));
	   		String orgPackage = getPropertyKey(elementTables.getAttribute("package"));
	   		String newPackage = orgPackage;
	   		String business = elementTables.getAttribute("business");
	   		
			if(target == null || "".equals(target)) {
				target = ".";
			}
			
	   		if(!UtilsText.isBlank(business)) {
	   			newPackage = newPackage.concat(".").concat(business);
	   		}
	   		Log.debug("target = " + target);
	   		Log.debug("package = " + newPackage);

	
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
	    			if("table".equals(element.getTagName())){	
	    				
	    				String orgTableName = element.getAttribute("name");
	    				
	    				if(orgTableName == null) {
	    					throw new NoneTableNameException("테이블 명이 존재 하지 않습니다.");
	    				}
	    				
	    				String camelTableName = UtilsText.convert2CamelCaseTable(orgTableName);
	    				
	    				createDao(target, camelTableName,newPackage);
	    				createMapper(target,orgTableName, camelTableName,newPackage);
	    				createDto(target, orgTableName, camelTableName, newPackage);
					}
				}
			}
			
		}
		

	}
	
	private  void createDao(String target,String tableName, String packagePh) throws Exception{
		
		target = UtilsText.concat(target,File.separator,packagePh.replace(".", "\\"));
		File makeTargetDirectory = new File(target);
		
		if(!makeTargetDirectory.isDirectory()) {
			makeTargetDirectory.mkdirs();
		}
		
//		Log.debug(target);
		
		Map<String,String> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", packagePh);


		setDirectoryForTemplate(new File("."));
		Template template = cfg.getTemplate("template/Dao.ftl");

		Writer file = new FileWriter (new File(UtilsText.concat(target,File.separator,tableName,"Dao.java")));
	    template.process(data, file);
	    file.flush();
	    file.close();

	}
	
	
	public void createMapper(String target,String orgTableName, String tableName, String packagePh) throws Exception{
		
		DBInfo dbInfo = processSql.getDbInfo();
		
		if(dbInfo.isOracle()) {
			processSql.callOracleColumn(orgTableName);				
		}else if(dbInfo.isMssql()) {
			processSql.callMssqlColumn(orgTableName);		
		}else if(dbInfo.isMysql()) {
			processSql.callMysqlColumn(orgTableName);		
		}else if(dbInfo.isMaria()) {
			processSql.callMariaColumn(orgTableName);		
		}
		
		List<Map<String, String>> columns = processSql.getColumns();
		List<Map<String, String>> pkColumns = processSql.getPrimaryColumns();
		
		System.out.println("column " + columns.size());
		System.out.println("pkColumns " + pkColumns.size());
		Map<String,String> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", packagePh);
		data.put("select", PreparedSql.select(orgTableName,packagePh,columns,pkColumns));
		data.put("insert", PreparedSql.insert(orgTableName,packagePh,columns,pkColumns));
		data.put("update", PreparedSql.update(orgTableName,packagePh,columns,pkColumns));
		data.put("delete", PreparedSql.delete(orgTableName,packagePh,columns,pkColumns));
		data.put("dto", UtilsText.concat(replaceDtoPackage(packagePh),".",tableName));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));

		setDirectoryForTemplate(new File("."));
		Template template = cfg.getTemplate("template/Mapper.ftl");

		Writer file = new FileWriter (new File(UtilsText.concat(target,File.separator,tableName,"Mapper.xml")));
	    template.process(data, file);
	    file.flush();
	    file.close();
	}
	
	
	public void createDto(String target,String orgTableName, String tableName, String newPackage) throws Exception{
		
		DBInfo dbInfo = processSql.getDbInfo();
		
		if(dbInfo.isOracle()) {
			processSql.callOracleColumn(orgTableName);				
		}else if(dbInfo.isMssql()) {
			processSql.callMssqlColumn(orgTableName);		
		}else if(dbInfo.isMysql()) {
			processSql.callMysqlColumn(orgTableName);		
		}else if(dbInfo.isMaria()) {
			processSql.callMariaColumn(orgTableName);		
		}
		
		List<Map<String, String>> columns = processSql.getColumns();
		List<Map<String, String>> pkColumns = processSql.getPrimaryColumns();
		
		newPackage = replaceDtoPackage(newPackage);
		
		System.out.println("column " + columns.size());
		System.out.println("pkColumns " + pkColumns.size());
		Map<String,Object> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", newPackage);
		data.put("dto", PreparedDto.dto(orgTableName,newPackage,columns,pkColumns));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));

		
		setDirectoryForTemplate(new File("."));
		Template template = cfg.getTemplate("template/Dto.ftl");

		Writer file = new FileWriter (new File(UtilsText.concat(target,File.separator,tableName,".java")));
	    template.process(data, file);
	    file.flush();
	    file.close();
	}
	
	
	private static String replaceDtoPackage(String newPackage) {
		
		newPackage = newPackage.replace(".repository", ".dto");
		newPackage = newPackage.replace(".dao", ".dto");
		return newPackage;
	}
	

	public static void main(String[] args) {

		String 	a = "kr.co.abcmart.dao.member";
		
		System.out.println(a.replace(".", "-"));
	}
}
