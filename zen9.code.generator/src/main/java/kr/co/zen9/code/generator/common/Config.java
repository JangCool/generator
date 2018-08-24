package kr.co.zen9.code.generator.common;

import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import kr.co.zen9.code.generator.parser.XmlParser;
import kr.co.zen9.code.generator.util.UtilsText;




public class Config {

    private static Properties env = new Properties();
	

    public static void loadConfiguration(XmlParser xp) {
    	
        try {

    		NodeList nodeList = xp.getDoc().getElementsByTagName("properties").item(0).getChildNodes();
    		int nodeListLength = nodeList.getLength();
    		
    		for (int i = 0; i < nodeListLength; i++) {
    			if(nodeList.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) nodeList.item(i);
	    			if("property".equals(element.getTagName())){					
						env.setProperty(element.getAttribute("key"), element.getAttribute("value"));
					}
				}
			}

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(String key) {
        String propertiesValue = env.getProperty(key);
        if (propertiesValue == null) {
            throw new ConfigValueNotFoundException(key + " 에 해당하는 환경 변수값을 찾을 수 없음");
        } else {
            return propertiesValue;
        }
    }

    public static String getString(String key, String defaultValue) {
        return env.getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new ConfigValueNotFoundException(key + " 에 해당하는 환경 변수값을 찾을 수 없음");
        } else {
            try {
                return Integer.parseInt(env.getProperty(key));
            } catch (NumberFormatException e) {
                throw new ConfigValueNotFoundException(key + " 에 값[" + value + "]");
            }
        }
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(env.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double getDouble(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new ConfigValueNotFoundException(key + " 에 해당하는 환경 변수값을 찾을 수 없음");
        } else {
            try {
                return Double.parseDouble(env.getProperty(key));
            } catch (NumberFormatException e) {
                throw new ConfigValueNotFoundException(key + " 에 값[" + value + "]");
            }
        }
    }

    public static double getDouble(String key, int defaultValue) {
        try {
            return Double.parseDouble(env.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    public static boolean getBoolean(String key) {
        return stringToBoolean(env.getProperty(key));
    }

    public static long getLong(String key) {
        String value = env.getProperty(key);
        if (value == null) {
            throw new ConfigValueNotFoundException(key + " 에 해당하는 환경 변수값을 찾을 수 없음");
        } else {
            try {
                return Long.parseLong(env.getProperty(key));
            } catch (NumberFormatException e) {
                throw new ConfigValueNotFoundException(key + " 에 값[" + value + "]");
            }
        }
    }
    
    public static long getLong(String key, long defaultValue) {
        try {
            return Long.parseLong(env.getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    private static boolean stringToBoolean(String value) {
        if (value == null) {
            return false;
        }
        if (value.equals("true")) {
            return true;
        }
        if (value.equals("on")) {
            return true;
        }
        if (value.equals("yes")) {
            return true;
        }
        if (value.equals("1")) {
            return true;
        }
        if (value.equals("Y")) {
            return true;
        }
        return false;
    }
    
    
	public static String getPropertyKey(String target) {
		if(UtilsText.getPropertyKey(target) != null) {
			return target = Config.getString(UtilsText.getPropertyKey(target));			
		}
		return null;
	}

}
