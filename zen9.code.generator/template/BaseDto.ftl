package ${package}.base;

import lombok.Data;

@Data
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
}
