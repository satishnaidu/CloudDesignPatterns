package com.sample.patterns.cache;

public class CacheAside {

	public static void main(String[] args) throws InterruptedException {
		// getEntity
		GetMyEntityAsync getMyEntityAsync = new GetMyEntityAsync();
		MyEntity myEntity = getMyEntityAsync.getMyEntity(101);
		System.out.println(myEntity);
		myEntity = getMyEntityAsync.getMyEntity(101);
		System.out.println(myEntity);
		Thread.sleep(35000);
		myEntity= getMyEntityAsync.getMyEntity(101);
		System.out.println(myEntity);
	}
}
