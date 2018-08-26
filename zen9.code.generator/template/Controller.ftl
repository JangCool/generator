package ${package};

import kr.co.abcmart.zfset.Common;

<#if '${annotation}' == 'RestController'>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.RequestMapping;

@${annotation}
@RequestMapping("${requestMapping}")
public class ${controllerName}Controller extends Common {

}				