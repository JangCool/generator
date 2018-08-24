package kr.co.zen9.code.generator.common;

import org.w3c.dom.Element;

import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsText;
import kr.co.zen9.code.generator.vo.PathVO;

public class Global {

	private static PathVO path;
	private static String sqlSession = Const.DEFAULT_SQL_SESSION;
	private static String daoPkg = Const.DEFAULT_PATH_DAO_PACKAGE ;
	
	public static void init(XmlParser xp) {
		Element global = (Element) xp.getDoc().getElementsByTagName("global").item(0);

		PathVO pv = new PathVO();

		if(global != null) {
			Element pathEl = (Element) global.getElementsByTagName("path").item(0);
			Element sqlSessionEl = (Element) global.getElementsByTagName("sqlsession").item(0);
			Element daoPkgEl = (Element) global.getElementsByTagName("daopkg").item(0);
			
			if( pathEl != null ) {
				
				Element template = (Element) pathEl.getElementsByTagName("template").item(0);
				Element source = (Element) pathEl.getElementsByTagName("source").item(0);
				Element mapper = (Element) pathEl.getElementsByTagName("mapper").item(0);
	
				pv.setTemplate(getTarget(template, null));
				pv.setSource(getTarget(source, Const.DEFAULT_PATH_SOURCES));
				pv.setMapper(getTarget(mapper, Const.DEFAULT_PATH_MAPPERS));

			}			
						
			if(sqlSessionEl != null) {
				Global.sqlSession = getTarget(sqlSessionEl, Const.DEFAULT_SQL_SESSION,"name");
			}
			
			if(daoPkgEl != null) {
				Global.daoPkg = getTarget(daoPkgEl, Const.DEFAULT_PATH_DAO_PACKAGE);
			}
			
		}else {
			pv.setTemplate(null);
			pv.setSource(Const.DEFAULT_PATH_SOURCES);
			pv.setMapper(Const.DEFAULT_PATH_MAPPERS);
		}
		
		Global.path = pv;
		
	}
	
	
	public static String getTarget(Element pathChild,String defaultTarget) {		
		return getTarget(pathChild, defaultTarget, "target");
	}
	
	public static String getTarget(Element pathChild,String defaultTarget,String attribute) {
		
		String target = defaultTarget;
		
		if(pathChild != null) {		
			String attrTarget = pathChild.getAttribute(attribute);
			target = Config.getPropertyKey(attrTarget);
			
			if(target == null) {
			
				if(UtilsText.isBlank(attrTarget)) {
					target = defaultTarget;
				}else {
					target = attrTarget;
				}				
			}
		}

		return target;
	}

	public static PathVO getPath() {
		return path;
	}

	public static String getSqlSession() {
		return sqlSession;
	}

	public static String getDaoPkg() {
		return daoPkg;
	}

	public static void setPath(PathVO path) {
		Global.path = path;
	}	
	
	
}
