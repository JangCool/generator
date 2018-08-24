package kr.co.zen9.code.generator.make;

import kr.co.zen9.code.generator.util.UtilsText;

public class GeneratorVO {
	
	private String target;
	private String orgPkg;
	private String pkg;
	private String business;
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		
		if(target == null || "".equals(target)) {
			target = ".";
		}
		
		this.target = target;
	}
	
	public String getOrgPkg() {
		return orgPkg;
	}
	
	public void setOrgPkg(String orgPkg) {
		this.orgPkg = orgPkg;
	}
	
	public String getPkg() {
		
		if(!UtilsText.isBlank(business)) {
			return pkg.concat(".").concat(business);
		}	   		
		
		return pkg;
	}
	
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	
	public String getBusiness() {
		return business;
	}
	
	public void setBusiness(String business) {
		this.business = business;
	}
	

}
