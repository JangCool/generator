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
import kr.co.zen9.code.generator.common.Global;
import kr.co.zen9.code.generator.common.Log;
import kr.co.zen9.code.generator.exception.NoneTableNameException;
import kr.co.zen9.code.generator.jdbc.ColumnsResultSet;
import kr.co.zen9.code.generator.jdbc.DBInfo;
import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsDate;
import kr.co.zen9.code.generator.util.UtilsText;
import kr.co.zen9.code.generator.vo.RepositoryVO;

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
	
	/**
	 * DB 에 접속 하여 해당 Table의 컬럼 정보를 가져 온다.
	 * 테이블이 있다면 컬럼 정보들을 가져 올 것이고 없다면 컬럼 정보를 가지고 오지 못한다. 
	 * 이 조건으로 테이블 유무를 결정 한다.
	 * 
	 * @param tableName 테이블 이름
	 * @return boolean
	 */
	public boolean getTableColumns(String tableName) {
		
		boolean result = true;
		
		DBInfo dbInfo = processSql.getDbInfo();
		
		if(dbInfo.isOracle()) {
			processSql.callOracleColumn(tableName);				
		}else if(dbInfo.isMssql()) {
			processSql.callMssqlColumn(tableName);		
		}else if(dbInfo.isMysql()) {
			processSql.callMysqlColumn(tableName);		
		}else if(dbInfo.isMaria()) {
			processSql.callMariaColumn(tableName);		
		}
		
		if(
			processSql.getColumns() == null || 
			processSql.getColumns() != null && processSql.getColumns().size() == 0 ) {
			result = false;
		}
		
		return result;
	}

	@Override
	public void generator() throws Exception {
				
		NodeList tables = xmlParser.getDoc().getElementsByTagName("tables");
		
   		int tablesLength = tables.getLength();
   		
   		if(tablesLength > 0 ) {
   			
   	   		Log.println("");
   	   		Log.debug("==================================== Repository Generator ======================================");
   	   		Log.debug("지금 Dao, Dto, Mapper 파일 생성을 시작 합니다.  ");
   	   		Log.debug("");
   	   		Log.debug("path mapper = " + Global.getPath().getMapper());
   	   		Log.debug("path source = " + Global.getPath().getSource());
   		}
   		

   		
		for (int i = 0; i < tablesLength; i++) {
			
			Node nodeTables = tables.item(i);
			Element elementTables = (Element) nodeTables;
			
			NodeList childTables = elementTables.getElementsByTagName("table");						
	   		int childTablesLength = childTables.getLength();
			
	   		RepositoryVO gv = new RepositoryVO();
	   		
	   		String tablesPkg  = getPropertyKey(elementTables.getAttribute("package"));
	   		String business   = getPropertyKey(elementTables.getAttribute("business"));
	   		String sqlSession = getPropertyKey(elementTables.getAttribute("sqlsession"));

	   		if(UtilsText.isBlank(tablesPkg)) {
	   			tablesPkg = Global.getDaoPkg();
	   		}
	   		
	   		if(UtilsText.isBlank(sqlSession)) {
	   			sqlSession = Global.getSqlSession();
	   		}
	   		
	   		gv.setOrgDaoPkg(tablesPkg);
	   		gv.setDaoPkg(gv.getOrgDaoPkg());
	   		gv.setBusiness(business);
	   		gv.setSqlSession(sqlSession);
	   		
	   		Log.debug("================================================================================================");
	   		Log.debug("sqlSession = " + gv.getSqlSession());
	   		Log.debug("business   = " + gv.getBusiness());
	   		Log.debug("package    = " + gv.getDaoPkg());
	   		Log.debug("================================================================================================");

	
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
	    			if("table".equals(element.getTagName())){	
	    				
	    				String orgTableName = element.getAttribute("name");
	    				
	    				if(orgTableName == null) {
	    					throw new NoneTableNameException("테이블 명이 존재 하지 않습니다.");
	    				}
	    				
	    				boolean isTable = getTableColumns(orgTableName);
	    				
	    				//테이블이 없을 경우 건너 뛴다.
	    				if(!isTable) {
	    					Log.debug(UtilsText.rpad(orgTableName, 30) + " 테이블이 존재 하지 않습니다.");
	    					continue;
	    				}

	    				
	    				String camelTableName = UtilsText.convert2CamelCaseTable(orgTableName);

	    				createDao(gv,orgTableName, camelTableName);
	    				createMapper(gv,orgTableName, camelTableName,element.getElementsByTagName("column"));
	    				createDto(gv, orgTableName, camelTableName);
	    				
	    				//호출된 컬럼 정보들을 제거 한다.
	    				processSql.clearColumns();
					}
				}
			}
			
		}
		

	}
	
	/**
	 *  dao 파일을 생성 한다.
	 * @param gv 생성에 필요한 정보를 담고 있는 GeneratorVO 객체
	 * @param orgTableName db에 존재하는 테이블명
	 * @param tableName camelcase로 변형된 테이블 명.
	 * @throws Exception
	 */
	private  void createDao(RepositoryVO gv,String orgTableName,String tableName) throws Exception{
		
		String field = UtilsText.convert2CamelCase(orgTableName);				
		
		Map<String,String> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", gv.getDaoPkg());
		data.put("dto", UtilsText.concat(replaceDtoPackage(gv.getDaoPkg()),".",tableName));
		data.put("field", field);
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("sqlsession", UtilsText.capitalize(gv.getSqlSession()));


		String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,gv.getDaoPkg().replace(".", "/"));		
		String path = UtilsText.concat(folder,File.separator,tableName,"Dao.java");

		String baseFolder = UtilsText.concat(folder,File.separator,"base");		
		String basePath = UtilsText.concat(baseFolder,File.separator,"Base",tableName,"Dao.java");
		
		
		writeTemplate("BaseDao", baseFolder, basePath, data);
		writeTemplate("Dao", folder, path, data);

	}
	
	/**
	 *  mapper 파일을 생성 한다.
	 * @param gv 생성에 필요한 정보를 담고 있는 GeneratorVO 객체
	 * @param orgTableName db에 존재하는 테이블명
	 * @param tableName camelcase로 변형된 테이블 명.
	 * @throws Exception
	 */	
	public void createMapper(RepositoryVO gv,String orgTableName, String tableName,NodeList columnNodeList) throws Exception{
		
		
		List<Map<String, String>> columns = processSql.getColumns();
		List<Map<String, String>> pkColumns = processSql.getPrimaryColumns();
		
		Map<String,String> data = new HashMap<>();
		data.put("tableName"			, tableName);
		data.put("package"				, gv.getDaoPkg());
		data.put("columns"				, PreparedSql.columns(orgTableName,gv.getDaoPkg(),columns,pkColumns,columnNodeList));
		data.put("selectByPrimaryKey"	, PreparedSql.selectByPrimaryKey(orgTableName,gv.getDaoPkg(),columns,pkColumns,columnNodeList));
		data.put("select"				, PreparedSql.select(orgTableName,gv.getDaoPkg(),columns,pkColumns,columnNodeList));
		data.put("insert"				, PreparedSql.insert(orgTableName,gv.getDaoPkg(),columns,pkColumns,columnNodeList));
		data.put("update"				, PreparedSql.update(orgTableName,gv.getDaoPkg(),columns,pkColumns,columnNodeList));
		data.put("delete"				, PreparedSql.delete(orgTableName,gv.getDaoPkg(),columns,pkColumns,columnNodeList));
		data.put("dto"					, UtilsText.concat(replaceDtoPackage(gv.getDaoPkg()),".",tableName));
		data.put("date"					, UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));

		String folder = UtilsText.concat(getPathMappers().getAbsolutePath());

		if(!UtilsText.isBlank(Global.getSqlSession())){
			folder = folder.concat(File.separator).concat(Global.getSqlSession());
		}
		
		if(!UtilsText.isBlank(gv.getBusiness())){
			folder = folder.concat(File.separator).concat(gv.getBusiness());
		}

		String path = UtilsText.concat(folder,File.separator,tableName,"Mapper.xml");
		
		String baseFolder = UtilsText.concat(folder,File.separator,"base");		
		String basePath = UtilsText.concat(baseFolder,File.separator,"Base",tableName,"Mapper.xml");
		
		
		
		writeTemplate("BaseMapper", baseFolder, basePath, data);
		writeTemplate("Mapper", folder, path, data);
	}
	
	/**
	 *  dto 파일을 생성 한다.
	 * @param gv 생성에 필요한 정보를 담고 있는 GeneratorVO 객체
	 * @param orgTableName db에 존재하는 테이블명
	 * @param tableName camelcase로 변형된 테이블 명.
	 * @throws Exception
	 */
	public void createDto(RepositoryVO gv,String orgTableName, String tableName) throws Exception{
		
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
		
		String pkgDto = replaceDtoPackage(gv.getDaoPkg());
		
		Map<String,Object> data = new HashMap<>();
		data.put("tableName", tableName);
		data.put("package", pkgDto);
		data.put("dto", PreparedDto.dto(orgTableName,pkgDto,columns,pkColumns));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));

		
		String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,pkgDto.replace(".", "/"));		
		String path = UtilsText.concat(folder,File.separator,tableName,".java");
		
		String baseFolder = UtilsText.concat(folder,File.separator,"base");		
		String basePath = UtilsText.concat(baseFolder,File.separator,"Base",tableName,".java");
		
		writeTemplate("BaseDto", baseFolder, basePath, data);
		writeTemplate("Dto", folder, path, data);
	}
		
	/**
	 * Dao를 만든 직후 Dto도 만들기 위해 패키지명 .dto로 변경 한다.
	 * @param newPackage
	 * @return
	 */
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
