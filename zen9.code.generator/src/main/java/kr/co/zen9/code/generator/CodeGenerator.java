package kr.co.zen9.code.generator;

import kr.co.zen9.code.generator.common.Config;
import kr.co.zen9.code.generator.common.Global;
import kr.co.zen9.code.generator.jdbc.ColumnsResultSet;
import kr.co.zen9.code.generator.jdbc.DBConnection;
import kr.co.zen9.code.generator.jdbc.DBInfo;
import kr.co.zen9.code.generator.make.BaseMake;
import kr.co.zen9.code.generator.make.MakeController;
import kr.co.zen9.code.generator.make.MakeDaoMapper;
import kr.co.zen9.code.generator.make.MakeService;
import kr.co.zen9.code.generator.parser.XmlParser;

public class CodeGenerator {
	
	public static XmlParser initParser() {

		String xmlPath = System.getProperty("generator.path");
		
		if(xmlPath == null ||  xmlPath != null && xmlPath.equals("")){
			xmlPath = ".";
		}
		
		XmlParser xp = new XmlParser(xmlPath);
			
		
		Config.loadConfiguration(xp);
		Global.init(xp);
		
		return xp;
	}

	public static void main(String[] args) {
		try {
			XmlParser xmlParser = initParser();
			DBInfo dbInfo = new DBInfo(xmlParser);
			 
			DBConnection dbConn = new DBConnection(dbInfo);
			ColumnsResultSet processSql = new ColumnsResultSet(dbConn);
			  
			
			BaseMake processController = new MakeController(xmlParser);
			processController.generator();
			
			BaseMake processService = new MakeService(xmlParser);
			processService.generator();
			
			BaseMake processDao = new MakeDaoMapper(xmlParser,processSql);
			processDao.generator();

			dbConn.close();

		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
