package kr.co.zen9.code.generator.make;

import java.io.File;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import kr.co.zen9.code.generator.common.Config;
import kr.co.zen9.code.generator.util.UtilsText;

public abstract class BaseMake {

	protected Configuration cfg;
	
	private File directoryForTemplate;
	
	private File targetPath;
	
	public BaseMake() {
	
		//Instantiate Configuration class  
		this.cfg = new Configuration(Configuration.VERSION_2_3_28);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

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
	
	public File getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(File targetPath) {
		this.targetPath = targetPath;
	}


	public abstract void generator() throws Exception;


}
