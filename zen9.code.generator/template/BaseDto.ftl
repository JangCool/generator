package ${package}.base;

public class Base${tableName} {

<#if dto??>
	<#list dto as fieldMap>	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     *
     */
	private ${fieldMap.javaType} ${fieldMap.field};
	
	</#list>
</#if>
<#if dto??>
	<#list dto as fieldMap>	
    /**
     * 이 set 메소드는 Code Generator를 통하여 생성 되었습니다.
     *
     * @param ${fieldMap.field}
     */
	public ${fieldMap.javaType} get${fieldMap.field2}(){
		return this.${fieldMap.field};
	}
	
    /**
     * 이 get 메소드는 Code Generator를 통하여 생성 되었습니다.
     *
     * @param ${fieldMap.field}
     */
	public void set${fieldMap.field2}(${fieldMap.javaType} ${fieldMap.field}){
		this.${fieldMap.field} = ${fieldMap.field};
	}
	
	</#list>
</#if>


}
