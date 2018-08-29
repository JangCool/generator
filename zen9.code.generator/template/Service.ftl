package ${package};
<#if '${proxyTargetProxy}' == 'true'>
import org.springframework.stereotype.Service;
import kr.co.abcmart.zfset.Common;

@Service
public class ${serviceName}Service extends Common{
<#else>
public interface ${serviceName}Service {
</#if>

}
