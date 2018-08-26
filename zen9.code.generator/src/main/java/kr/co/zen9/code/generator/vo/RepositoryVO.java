package kr.co.zen9.code.generator.vo;

import kr.co.zen9.code.generator.util.UtilsText;

public class RepositoryVO {
	
//	private String target;
	private String orgDaoPkg;
	private String daoPkg;
	private String business;
	private String sqlSession;
	
//	public String getTarget() {
//		return target;
//	}
//	public void setTarget(String target) {
//		
//		if(target == null || "".equals(target)) {
//			target = ".";
//		}
//		
//		this.target = target;
//	}
	
	public String getOrgDaoPkg() {
		return orgDaoPkg;
	}
	
	public void setOrgDaoPkg(String orgDaoPkg) {
		this.orgDaoPkg = orgDaoPkg;
	}
	
	public String getDaoPkg() {	
		
		String returnValue = daoPkg;

		if(!UtilsText.isBlank(getSqlSession())){
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}
		
		if(!UtilsText.isBlank(getBusiness())){
			returnValue = returnValue.concat(".").concat(getBusiness());
		}

		return returnValue;
	}
	
	public void setDaoPkg(String daoPkg) {
		this.daoPkg = daoPkg;
	}
	
	public String getBusiness() {
		return business;
	}
	
	public void setBusiness(String business) {
		this.business = business;
	}
	
	public String getSqlSession() {
		return sqlSession;
	}
	public void setSqlSession(String sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	

}
