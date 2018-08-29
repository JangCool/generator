package kr.co.zen9.code.generator.vo;

import kr.co.zen9.code.generator.util.UtilsText;

public class ControllerVO extends BaseVO{

	private String requestMapping;

	public String getRequestMapping() {
		return requestMapping;
	}

	public void setRequestMapping(String requestMapping) {
		this.requestMapping = requestMapping;
	}
	

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
	

}
