package kr.co.zen9.code.generator.common;

/**
 *
 * 클래스명: <code>ConfigValueNotFoundException</code>
 * 
 * <pre>
 * Config Value Not Found Exception
 * </pre>
 *
 * @date 2010. 8. 11.
 * @author 이경연
 *
 */
@SuppressWarnings("serial")
public class ConfigValueNotFoundException extends RuntimeException {

   public ConfigValueNotFoundException(String s) {
       super(s);
   }

   public ConfigValueNotFoundException(Exception e) {
       super(e);
   }
}