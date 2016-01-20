package com.sample.patterns.cache;

import java.util.HashMap;
import java.util.Map;

public class MockDatabase {

	private static Map<String, MyEntity> entityMap = new HashMap<>();

	static {
		MyEntity myEntity = new MyEntity();
		myEntity.setId("101");
		myEntity.setRow1("user1");
		myEntity.setRow1("address");
		entityMap.put(myEntity.getId(), myEntity);

		MyEntity myEntity2 = new MyEntity();
		myEntity2.setId("102");
		myEntity2.setRow1("user1");
		myEntity2.setRow1("address");
		entityMap.put(myEntity2.getId(), myEntity2);

	}

	public static MyEntity getMyEntity(int id) {

		MyEntity myEntity = entityMap.get(String.valueOf(id));

		return myEntity;
	}

	public static boolean writeEntity(MyEntity myEntity) {

		entityMap.put(myEntity.getId(), myEntity);
		return true;

	}
}
