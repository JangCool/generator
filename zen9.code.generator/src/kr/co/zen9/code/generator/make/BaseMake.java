package kr.co.zen9.code.generator.make;

import java.io.File;

import org.w3c.dom.Element;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import kr.co.zen9.code.generator.common.Config;
import kr.co.zen9.code.generator.common.Const;
import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsText;

public abstract class BaseMake {

	protected XmlParser xmlParser;

	protected Configuration cfg;
	
	private File directoryForTemplate;
	
	private File pathSources = new File(Const.DEFAULT_PATH_SOURCES);

	private File pathMappers = new File(Const.DEFAULT_PATH_MAPPERS);
	
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
		
		Element pathElement = (Element) xmlParser.getDoc().getElementsByTagName("path").item(0);
		if(pathElement != null) {

			Element template = (Element) pathElement.getElementsByTagName("template").item(0);
			Element sources = (Element) pathElement.getElementsByTagName("source").item(0);
			Element mappers = (Element) pathElement.getElementsByTagName("mapper").item(0);

			try {
				if(template != null) {
					String pathTemplate = getPropertyKey(template.getAttribute("target"));
						setDirectoryForTemplate(new File(pathTemplate == null ? template.getAttribute("target") : pathTemplate));
				}else {
					setDirectoryForTemplate(new File("."));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(sources != null) {

				String pathSource = getPropertyKey(sources.getAttribute("target"));
				this.pathSources = new File(pathSource == null ? sources.getAttribute("target") : pathSource);
			}			
			
			if(mappers != null) {
				
				String pathMapper = getPropertyKey(mappers.getAttribute("target"));
				this.pathMappers = new File(pathMapper == null ? mappers.getAttribute("target") : pathMapper);

			}			
		}
	}
	
	public File getPathSources() {
		return pathSources;
	}

	public File getPathMappers() {
		return pathMappers;
	}

	public abstract void generator() throws Exception;


}
