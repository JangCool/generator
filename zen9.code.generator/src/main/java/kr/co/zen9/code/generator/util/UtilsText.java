package kr.co.zen9.code.generator.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsText {
	
	public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;	
	}
	
	public static String concat(String ...arrStr) {
		
		StringBuilder sb = new StringBuilder();
		
		for (String concatStr : arrStr) {
			sb.append(concatStr);
		}
		
		return sb.toString();
		
	}
	
	public static String getPropertyKey(String regxValue) {
		
		Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher matcher = pattern.matcher(regxValue);
		if (matcher.find())	{
		   return matcher.group(1);
		}
		
		return null;
	}

	 /**
     * underscore ('_') 가 포함되어 있는 문자열을 Camel Case ( 낙타등
     * 표기법 - 단어의 변경시에 대문자로 시작하는 형태. 시작은 소문자) 로 변환해주는
     * utility 메서드 ('_' 가 나타나지 않고 첫문자가 대문자인 경우도 변환 처리
     * 함.)
     * @param underScore
     *        - '_' 가 포함된 변수명
     * @return Camel 표기법 변수명
     */
    public static String convert2CamelCase(String underScore) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0
            && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
               	    result.append(Character.toLowerCase(currentChar));                		
                }
            }
        }
        return result.toString();
    }
    
	 /**
     * 
     * 테이블 명을 클래스 명으로 변환 한다.
     * ex) TB_AREA = TbArea
     * @param underScore
     *        - '_' 가 포함된 변수명
     * @return Camel 표기법 변수명
     */
    public static String convert2CamelCaseTable(String underScore) {

        // '_' 가 나타나지 않으면 이미 camel case 로 가정함.
        // 단 첫째문자가 대문자이면 camel case 변환 (전체를 소문자로) 처리가
        // 필요하다고 가정함. --> 아래 로직을 수행하면 바뀜
        if (underScore.indexOf('_') < 0
            && Character.isLowerCase(underScore.charAt(0))) {
            return underScore;
        }
        StringBuilder result = new StringBuilder();
        boolean nextUpper = false;
        int len = underScore.length();

        for (int i = 0; i < len; i++) {
            char currentChar = underScore.charAt(i);
            if (currentChar == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(currentChar));
                    nextUpper = false;
                } else {
                	if(i == 0) {
                        result.append(Character.toUpperCase(currentChar));
                	}else {
                        result.append(Character.toLowerCase(currentChar));                		
                	}
                }
            }
        }
        return result.toString();
    }
    
    public static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int newCodePoints[] = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint; // copy the remaining ones
            inOffset += Character.charCount(codepoint);
         }
        return new String(newCodePoints, 0, outOffset);
    }
    
    
	/**
	*	왼쪽에 공백을 채워 전체 width길이 만큼의 문자열을 반환한다.
	*/
	public static String rpad( String origin, int width ) {
		return lpad(origin, width, ' ');
	}
	
	public static String rpad(String origin, int width, char filler) {
		if( origin == null ) origin = "";
		StringBuffer buf = new StringBuffer();
	    int space = width - origin.length();
	    while ( space-- > 0 ) {
	        buf.append(filler);
	    }
		buf.append(origin);
	    return buf.toString();
	}
	
	/**
	*	문자열의 오른쪽에 공백을 채워 전체 width길이 만큼의 문자열을 반환한다.
	*/
	public static String lpad(String origin, int width) {
		return rpad(origin, width, ' ');
	}
	
	public static String lpad(String origin, int width, char filler) {
		if( origin == null ) origin = "";
		StringBuffer buf = new StringBuffer(origin);
		int space = width - origin.length();
		while( space--  > 0) {
			buf.append(filler);
		}
		return buf.toString();
	}
	
	/**
	 * <pre>
	 * 문자열을 주어진 포맷으로 일치시킨다.
	 * 용례) matchFormat("20010101", "####/##/##") -> "2001/01/01"
	 *       matchFormat("12345678", "##/## : ##") -> "12/34 : 56"
	 * </pre>
	 * @param	str			원본문자열
	 * @param	format		결과 포맷형태('#'문자에 원본 문자가 위치, 그외 문자는 그대로 표시)
	 * @return 	포맷으로 변환된 문자열
	 */
	public static String format(String str, String format) {
		if(str == null || str.length() == 0 ) return str;
		int len = format.length();
		char[] result = new char[len];
		for(int i=0,j=0; i<len; i++,j++) {
			if(format.charAt(i)=='#') {
				try {
					result[i]= str.charAt(j);
				}catch(StringIndexOutOfBoundsException e) {
					result[i]= '\u0000';
				}
			} else {
				result[i]= format.charAt(i);
				j--;
			}
		}
		return new String(result);
	}
    
    
}
