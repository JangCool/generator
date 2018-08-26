package kr.co.zen9.code.generator.make;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import kr.co.zen9.code.generator.CodeGenerator;
import kr.co.zen9.code.generator.common.Config;
import kr.co.zen9.code.generator.common.Global;
import kr.co.zen9.code.generator.common.Log;
import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsText;

public abstract class BaseMake {

	protected XmlParser xmlParser;

	protected Configuration cfg;
	
	private File directoryForTemplate;
	
	private File pathSources = new File(Global.getPath().getSource());

	private File pathMappers = new File(Global.getPath().getMapper());
	
	private String daoPkg = Global.getDaoPkg();
	
	public BaseMake() {
		this(null);
	}
	
	public BaseMake(XmlParser xmlParser) {
		
		this.xmlParser = xmlParser;
		this.cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		
		init();
	}
	
	public File getDirectoryForTemplate() {
		return directoryForTemplate;
	}

	public void setDirectoryForTemplate(File directoryForTemplate) throws Exception {
		this.directoryForTemplate = directoryForTemplate;
		cfg.setDirectoryForTemplateLoading(directoryForTemplate);
	}
	
	
	public String getPropertyKey(String target) {
		if(UtilsText.getPropertyKey(target) != null) {
			return target = Config.getString(UtilsText.getPropertyKey(target));			
		}
		return target;
	}
	

	private void init() {
				
		try {
			if(Global.getPath().getTemplate() != null) {
					setDirectoryForTemplate(new File(Global.getPath().getTemplate()));
			}else {
				cfg.setClassForTemplateLoading(CodeGenerator.class,"/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File getPathSources() {
		return pathSources;
	}

	public File getPathMappers() {
		return pathMappers;
	}
	
	public String getDaoPkg() {
		return daoPkg;
	}

	/**
	 * 템플릿 파일을 이용하여 Local 경로에 파일을 생성한다.
	 * @param templateFileName
	 * @param folder
	 * @param path
	 * @param data
	 * @throws Exception
	 */
	public void writeTemplate(String templateFileName,String folder,String path ,Map data) throws Exception {
		
		Template template = cfg.getTemplate(UtilsText.concat("template/",templateFileName,".ftl"));

		File makeTargetDirectory = new File(folder);
		
		boolean isDirectory = makeTargetDirectory.isDirectory();
		
		
		if(!isDirectory) {
			makeTargetDirectory.mkdirs();
		}

	    String fileName = UtilsText.rpad(path.substring(path.lastIndexOf(File.separator)+1, path.length()), 30);

		//Base 파일이 아닐 경우 이미 생성 되어 있으면  파일을 다시 쓰지 않고 넘어간다.
		if( !(new File(path).isFile() && !templateFileName.startsWith("Base")) ) {
			
			Writer file = new FileWriter (path);
			template.process(data, file);
		    file.flush();
		    file.close();
		    
			Log.debug(UtilsText.rpad(fileName, 30) + " 파일이 생성 되었습니다.");

		}else if( (new File(path).isFile() && !templateFileName.startsWith("Base")) ) {
			Log.debug(UtilsText.rpad(fileName, 30) + " 파일이 이미 존재 합니다.");
		}
	}
	
	public abstract void generator() throws Exception;


}
