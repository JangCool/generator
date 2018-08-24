package kr.co.zen9.code.generator.make;

import java.io.File;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import kr.co.zen9.code.generator.CodeGenerator;
import kr.co.zen9.code.generator.common.Config;
import kr.co.zen9.code.generator.common.Global;
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
		return null;
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

	public abstract void generator() throws Exception;


}
