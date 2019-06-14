package com.information.retrieval.system.page.rank.domains;

public class RankUrl {

	private float pageRank;
	private String url;
	public float getPageRank() {
		return pageRank;
	}
	public void setPageRank(float pageRank) {
		this.pageRank = pageRank;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public RankUrl(float pageRank, String url) {
		super();
		this.pageRank = pageRank;
		this.url = url;
	}
	
	
}
