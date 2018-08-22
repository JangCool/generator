package kr.co.zen9.code.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import kr.co.zen9.code.generator.jdbc.DBConnection;
import kr.co.zen9.code.generator.jdbc.DBInfo;
import kr.co.zen9.code.generator.jdbc.ProcessSql;

public class CodeGenerator {

	public static void main(String[] args) {
		try {
					  
					  
		DBInfo dbInfo = new DBInfo();
		  
		DBConnection dbConn = new DBConnection(dbInfo);
		ProcessSql processSql = new ProcessSql(dbConn);
		processSql.callOracleColumn();
		dbConn.close();
		  
		  
		processSql.getColumns();
		processSql.getPrimaryColumns();
			  
		//Instantiate Configuration class  
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setDirectoryForTemplateLoading(new File("."));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		//Create Data Model
		Map<String, Object> map = new HashMap<>();
		map.put("package", "kr.co.abcmart.repository.master.sample");
		map.put("business", "member");
		map.put("table", "Sample");
		//	            List<URL> references = new ArrayList<>();
		//	            references.add(new URL("http://url1.com","URL One", null));
		//	            references.add(new URL("http://url2.com","URL Two"));
		//	            references.add(new URL("http://url3.com","URL Three"));
		//	            map.put("references", references);
		//Instantiate template
		Template template = cfg.getTemplate("template/Dao.ftl");
		//Console output
		//	            Writer console = new OutputStreamWriter(System.out);
		//	            template.process(map, console);
		//	            console.flush();
		// File output
		Writer file = new FileWriter (new File("D:\\project\\abc\\workspace\\zen9.code.generator\\template\\Sample.java"));
		    template.process(map, file);
		    file.flush();
		    file.close();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (TemplateException e) {
		    e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
