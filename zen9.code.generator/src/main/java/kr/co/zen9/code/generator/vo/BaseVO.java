package kr.co.zen9.code.generator.vo;

public class BaseVO {

	private String business;
	private String pkg;
	private String suffixPkg;

	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getPkg() {
		return pkg;
	}
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}
	public String getSuffixPkg() {
		return suffixPkg;
	}
	public void setSuffixPkg(String suffixPkg) {
		this.suffixPkg = suffixPkg;
	}

}
