package com.information.retrieval.system.crawler.constants;

public enum Constants {
	
	Colon(":"),
    POLITENESS_TIME_IN_SECONDS("3");
	
	String value;

	private Constants(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
