package cn.usmaker.ben.view.util;

public class UrlBean {

	private int    versionCode;
	private String desc;
	private String downloadUrl;
	
	public UrlBean() {
		// TODO Auto-generated constructor stub
	}
 
	public UrlBean(int versionCode, String desc, String downloadUrl) {
		 
		this.versionCode = versionCode;
		this.desc = desc;
		this.downloadUrl = downloadUrl;
	}


	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	
	
}
