package kr.co.zen9.code.generator.make;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.co.zen9.code.generator.common.Global;
import kr.co.zen9.code.generator.common.Log;
import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsText;
import kr.co.zen9.code.generator.vo.ControllerVO;

public class MakeController extends BaseMake{
	
	public MakeController() {
		super();
	}
	
	public MakeController(XmlParser xmlParser) {
		super(xmlParser);
	}

	@Override
	public void generator() throws Exception {

		
		NodeList controllers = xmlParser.getDoc().getElementsByTagName("controllers");
		
   		int controllersLength = controllers.getLength();
   		if(controllersLength > 0 ) {
	   			
	   		Log.println("");
	   		Log.debug("==================================== Controller Generator ======================================");
	   		Log.debug("지금 Controller 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
	   		Log.debug("path source = " + Global.getPath().getSource());
   		
   		}
   		
		for (int i = 0; i < controllersLength; i++) {
			
			Node nodeTables = controllers.item(i);
			Element elementTables = (Element) nodeTables;
			
	   		String pkg  = getPropertyKey(elementTables.getAttribute("package"));
	   		String suffixPkg  = getPropertyKey(elementTables.getAttribute("suffix-package"));
	   		String business   = getPropertyKey(elementTables.getAttribute("business"));
	   		String controllersType   = getPropertyKey(elementTables.getAttribute("type"));

	   		if(UtilsText.isBlank(pkg)) {
	   			pkg = Global.getControllerPkg();
	   		}

	  		ControllerVO cv = new ControllerVO();
			cv.setPkg(pkg);
	   		cv.setSuffixPkg(suffixPkg);
	   		cv.setBusiness(business);
	   		
	   		Log.debug("================================================================================================");
	   		Log.debug("business   = " + cv.getBusiness());
	   		Log.debug("package    = " + cv.getPkg());
	   		Log.debug("================================================================================================");

			
			NodeList childTables = elementTables.getElementsByTagName("controller");						
	   		int childTablesLength = childTables.getLength();
	   		
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
			   		String controllerName  = getPropertyKey(element.getAttribute("name"));
			   		String requestMapping  = getPropertyKey(element.getAttribute("request-mapping"));
			   		String type			   = getPropertyKey(element.getAttribute("type"));
			   		
			   		
			   		if(UtilsText.isBlank(type)) {
			   			type = controllersType;
			   		}
			   		
					if(!UtilsText.isBlank(type) && "rest".equals(type)){
						type = "RestController";
					}else {
						type = "Controller";
					}
			   		
					String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,cv.getPkg().replace(".", "/"));		
					String path = UtilsText.concat(folder,File.separator,controllerName,"Controller.java");
					
					if(!UtilsText.isBlank(requestMapping)) {
						cv.setRequestMapping(requestMapping);
					}else {
						if(!UtilsText.isBlank(cv.getBusiness())) {
							cv.setRequestMapping(cv.getBusiness().toLowerCase());
						}else {
							cv.setRequestMapping(controllerName.toLowerCase());
						}
					}
					
			  		
					Map<String,String> data = new HashMap<>();
					data.put("controllerName"		, controllerName);
					data.put("package"				, cv.getPkg());
					data.put("requestMapping"		, cv.getRequestMapping());
					data.put("annotation"			, type);
					
					writeTemplate("Controller", folder, path, data);

				}
			}
		}
	}
}
