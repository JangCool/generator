package kr.co.zen9.code.generator.vo;

import kr.co.zen9.code.generator.util.UtilsText;

public class ServiceVO extends BaseVO{

	@Override
	public String getPkg() {
		
		String returnValue = super.getPkg();
		
		if(!UtilsText.isBlank(getBusiness())){
			returnValue = returnValue.concat(".").concat(getBusiness());
		}
		
		return returnValue;
	}
	

}
