package org.kwet.giteway.model;

public enum SearchType {

	OWNER("owner"),KEYWORD("keyword");
	
	private String type;
	
	private SearchType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static SearchType getSearchType(String type){
		for(SearchType st:SearchType.values()){
			if(st.type.equals(type)){
				return st;
			}
		}
		throw new RuntimeException("Unknow type : "+type);
	}
	
}
