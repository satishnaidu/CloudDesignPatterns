package com.sample.patterns.cache;

import java.text.MessageFormat;

public class GetMyEntityAsync {

	private static final int TIME_OUT = 30000;

	public MyEntity getMyEntity(int id) {
		System.out.println("in get my entity with id: " + id);
		String key = MessageFormat.format("StoreWithCache_GetMyEntity_{0}", id);
		boolean cacheException = false;
		MyEntity myEntity = null;

		try {
			// Try to get the entity from the cache.
			myEntity = DataCache.getCacheItem(key);
			if (myEntity != null) {
				System.out.println("my entity is found in cache");
				return myEntity;
			}

		} catch (DataCacheException e) {
			// If there is a cache related issue, raise an exception
			// and avoid using the cache for the rest of the call.
			cacheException = true;
		}
		System.out
				.println("my entity is not found in cache, so fetching from database");
		myEntity = MockDatabase.getMyEntity(id);
		if (!cacheException) {
			if (myEntity != null) {

				try {
					// Put the item in the cache with a custom expiration time
					// that
					// depends on how critical it might be to have stale data.
					System.out
							.println("placing the entity for caching with timeout "
									+ TIME_OUT);
					DataCache.putCacheItem(key, myEntity, TIME_OUT);
				} catch (DataCacheException e) {
					// If there is a cache related issue, ignore it
					// and just return the entity.
				}
			}
		}

		return myEntity;
	}

	public void writeEntity(MyEntity myEntity) {
		boolean status = MockDatabase.writeEntity(myEntity);
		if (status) {
			try {
				DataCache.putCacheItem(myEntity.getId(), myEntity, TIME_OUT);
			} catch (DataCacheException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
