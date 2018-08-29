package kr.co.zen9.code.generator.vo;

import kr.co.zen9.code.generator.util.UtilsText;

public class ServiceVO extends BaseVO{

	private String proxyTargetProxy;
	
	@Override
	public String getPkg() {
		
		String returnValue = super.getPkg();
		
		if(!UtilsText.isBlank(getBusiness())){
			returnValue = returnValue.concat(".").concat(getBusiness());
		}
		
		if(!UtilsText.isBlank(getSuffixPkg())){
			returnValue = returnValue.concat(".").concat(getSuffixPkg());
		}
		
		
		return returnValue;
	}

	public String getProxyTargetProxy() {
		return proxyTargetProxy;
	}

	public void setProxyTargetProxy(String proxyTargetProxy) {
		this.proxyTargetProxy = proxyTargetProxy;
	}
	
}
