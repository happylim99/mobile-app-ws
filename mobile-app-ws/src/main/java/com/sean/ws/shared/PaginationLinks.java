package com.sean.ws.shared;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PaginationLinks {
	private String first;
	private String previous;
	private List<String> between;
	private String next;
	private String last;
	
	public PaginationLinks() {
		super();
	}

	public PaginationLinks(Object[] pageInfo) {
		super();
		Map<String, Object[]> page = new Utils().linksConstructor(
				(Boolean) pageInfo[0],
				(Boolean) pageInfo[1],
				(int) pageInfo[2],
				(int) pageInfo[3],
				(int) pageInfo[4],
				(int) pageInfo[5],
				(String[]) pageInfo[6],
				(int) pageInfo[7]);
		
		this.first = page.get("first")[0].toString();
		this.previous = page.get("previous")[0].toString();
		
		Object[] objAry = page.get("between");
		
		String[] stringArray = Arrays.copyOf(objAry, objAry.length, String[].class);
		//convert object array to string array
		//String[] stringArray = Arrays.stream(objAry).map(Object::toString).toArray(String[]::new);
		
		this.between = Arrays.asList(stringArray);
		//System.out.println(this.between.get(0).);
		this.next = page.get("next")[0].toString();
		this.last = page.get("last")[0].toString();
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public List<String> getBetween() {
		return between;
	}

	public void setBetween(List<String> between) {
		this.between = between;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	
}
