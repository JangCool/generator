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
import kr.co.zen9.code.generator.vo.ServiceVO;

public class MakeService extends BaseMake{
	
	public MakeService() {
		super();
	}
	
	public MakeService(XmlParser xmlParser) {
		super(xmlParser);
	}

	@Override
	public void generator() throws Exception {

		
		NodeList services = xmlParser.getDoc().getElementsByTagName("services");
		
   		int servicesLength = services.getLength();
   		if(servicesLength > 0 ) {
	
	   		Log.println("");
	   		Log.debug("==================================== Service Generator ======================================");
	   		Log.debug("지금 Service 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
	   		Log.debug("path source = " + Global.getPath().getSource());
   		}
   		
		for (int i = 0; i < servicesLength; i++) {
			
			Node nodeTables = services.item(i);
			Element elementTables = (Element) nodeTables;
			
	   		String pkg  		= getPropertyKey(elementTables.getAttribute("package"));
	   		String business		= getPropertyKey(elementTables.getAttribute("business"));

	   		if(UtilsText.isBlank(pkg)) {
	   			pkg = Global.getServicePkg();
	   		}

	   		ServiceVO cv = new ServiceVO();
			cv.setPkg(pkg);
	   		cv.setBusiness(business);
	   		
	   		Log.debug("================================================================================================");
	   		Log.debug("business   = " + cv.getBusiness());
	   		Log.debug("package    = " + cv.getPkg());
	   		Log.debug("================================================================================================");

			
			NodeList childTables = elementTables.getElementsByTagName("service");						
	   		int childTablesLength = childTables.getLength();
	   		
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
			   		String serviceName  = getPropertyKey(element.getAttribute("name"));
			   		
			   	
					String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,cv.getPkg().replace(".", "/"));		
					String path = UtilsText.concat(folder,File.separator,serviceName,"Service.java");
					
					String folderImpl = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,cv.getPkg().replace(".", "/"));		
					String pathImpl = UtilsText.concat(folder,File.separator,serviceName,"ServiceImpl.java");
					
			  		
					Map<String,String> data = new HashMap<>();
					data.put("serviceName"			, serviceName);
					data.put("package"				, cv.getPkg());
					
					writeTemplate("Service", folder, path, data);
					writeTemplate("ServiceImpl", folderImpl, pathImpl, data);

				}
			}
		}
	}
}
