package com.sample.patterns.cache;

public class MyEntity {

	private String id;
	private String row1;
	private String row2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRow1() {
		return row1;
	}

	public void setRow1(String row1) {
		this.row1 = row1;
	}

	public String getRow2() {
		return row2;
	}

	public void setRow2(String row2) {
		this.row2 = row2;
	}

	@Override
	public String toString() {
		return "MyEntity [id=" + id + ", row1=" + row1 + ", row2=" + row2 + "]";
	}

}
