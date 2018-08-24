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
import kr.co.zen9.code.generator.jdbc.ColumnsResultSet;
import kr.co.zen9.code.generator.jdbc.DBInfo;
import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsDate;
import kr.co.zen9.code.generator.util.UtilsText;

public class MakeDaoMapper extends BaseMake{

	private ColumnsResultSet processSql;
	private XmlParser xmlParser;
	
	public MakeDaoMapper() {
		super();
	}
	
	public MakeDaoMapper(XmlParser xmlParser, ColumnsResultSet processSql) {
		super(xmlParser);
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
			
	   		GeneratorVO gv = new GeneratorVO();
	   		gv.setTarget(getPropertyKey(elementTables.getAttribute("target")));
	   		gv.setOrgPkg(getPropertyKey(elementTables.getAttribute("package")));
	   		gv.setPkg(gv.getOrgPkg());
	   		gv.setBusiness(elementTables.getAttribute("business"));
	   		
	   		Log.debug("target = " + gv.getTarget());
	   		Log.debug("package = " + gv.getPkg());

	
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
	    			if("table".equals(element.getTagName())){	
	    				
	    				String orgTableName = element.getAttribute("name");
	    				
	    				if(orgTableName == null) {
	    					throw new NoneTableNameException("테이블 명이 존재 하지 않습니다.");
	    				}
	    				
	    				String camelTableName = UtilsText.convert2CamelCaseTable(orgTableName);

	    				createDao(gv,orgTableName, camelTableName);
	    				createMapper(gv,orgTableName, camelTableName,element.getElementsByTagName("column"));
	    				createDto(gv, orgTableName, camelTableName);
					}
				}
			}
			
		}
		

	}
	
	private  void createDao(GeneratorVO gv,String orgTableName,String tableName) throws Exception{
		
		String field = UtilsText.convert2CamelCase(orgTableName);				
		
		Map<String,String> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", gv.getPkg());
		data.put("dto", UtilsText.concat(replaceDtoPackage(gv.getPkg()),".",tableName));
		data.put("field", field);
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		
		String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,gv.getPkg().replace(".", "/"));		
		String path = UtilsText.concat(folder,File.separator,tableName,"Dao.java");
		
		writeTemplate("Dao", folder, path, data);

	}
	
	
	public void createMapper(GeneratorVO gv,String orgTableName, String tableName,NodeList columnNodeList) throws Exception{
		
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
		
		Map<String,String> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", gv.getPkg());
		data.put("select", PreparedSql.select(orgTableName,gv.getPkg(),columns,pkColumns,columnNodeList));
		data.put("insert", PreparedSql.insert(orgTableName,gv.getPkg(),columns,pkColumns,columnNodeList));
		data.put("update", PreparedSql.update(orgTableName,gv.getPkg(),columns,pkColumns,columnNodeList));
		data.put("delete", PreparedSql.delete(orgTableName,gv.getPkg(),columns,pkColumns,columnNodeList));
		data.put("dto", UtilsText.concat(replaceDtoPackage(gv.getPkg()),".",tableName));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));

		
		String folder = UtilsText.concat(getPathMappers().getAbsolutePath());
		if(!UtilsText.isBlank(gv.getBusiness())){
			folder = folder.concat(File.separator).concat(gv.getBusiness());
		}
		String path = UtilsText.concat(folder,File.separator,tableName,"Mapper.xml");
		
		writeTemplate("Mapper", folder, path, data);
	}
	
	
	public void createDto(GeneratorVO gv,String orgTableName, String tableName) throws Exception{
		
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
		
		String pkgDto = replaceDtoPackage(gv.getPkg());
		
		
		Map<String,Object> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", pkgDto);
		data.put("dto", PreparedDto.dto(orgTableName,pkgDto,columns,pkColumns));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));

		
		String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,pkgDto.replace(".", "/"));		
		String path = UtilsText.concat(folder,File.separator,tableName,".java");
		
		
		writeTemplate("Dto", folder, path, data);
	}
	
	private void writeTemplate(String templateFileName,String folder,String path ,Map data) throws Exception {
		
		Template template = cfg.getTemplate(UtilsText.concat("template/",templateFileName,".ftl"));

		File makeTargetDirectory = new File(folder);
		
		if(!makeTargetDirectory.isDirectory()) {
			makeTargetDirectory.mkdirs();
		}
		
		Writer file = new FileWriter (path);
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
