package kr.co.zen9.code.generator.vo;

import kr.co.zen9.code.generator.util.UtilsText;

public class RepositoryVO extends BaseVO{
	
	private String orgDaoPkg;
	private String business;
	private String sqlSession;
	private String alias;
	private String prefix;
	private String suffix;

	
	public String getOrgDaoPkg() {
		return orgDaoPkg;
	}
	
	public void setOrgDaoPkg(String orgDaoPkg) {
		this.orgDaoPkg = orgDaoPkg;
	}
	
	public String getPkg() {	
		
		String returnValue = super.getPkg();

		
		if(!UtilsText.isBlank(getBusiness())){
			returnValue = returnValue.concat(".").concat(getBusiness());
		}
		
		if(!UtilsText.isBlank(getSuffixPkg())){
			returnValue = returnValue.concat(".").concat(getSuffixPkg());
		}
		
		if(!UtilsText.isBlank(getSqlSession())){
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}
		


		return returnValue;
	}

	
	public String getMapperPkg() {	
		
		String returnValue = "";

		if(!UtilsText.isBlank(getSqlSession())){
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}
		
		if(!UtilsText.isBlank(getBusiness())){
			returnValue = returnValue.concat(".").concat(getBusiness());
		}

		return returnValue;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	
	

}
