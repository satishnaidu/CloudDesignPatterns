package com.sample.patterns.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class DataCache {

	private static Map<String, MyEntity> cacheItem = new HashMap<String, MyEntity>();

	private static ScheduledExecutorService executerService;

	private static ScheduledFuture<?> scheduledFuture;

	private static final int NUM_OF_THREADS = 2;

	private static final boolean DONT_INTERRUPT_IF_RUNNING = true;

	static {
		executerService = Executors.newScheduledThreadPool(NUM_OF_THREADS);
	}

	public DataCache() {

	}

	public static MyEntity getCacheItem(String key) throws DataCacheException {
		if (key == null) {
			throw new DataCacheException("cache key cannot be null");
		}
		System.out.println("trying to fetch data from cache");
		MyEntity obj = cacheItem.get(key);

		return obj;
	}

	public static MyEntity putCacheItem(String key, MyEntity myEntity,
			int timeout) throws DataCacheException {
		cacheItem.put(key, myEntity);
		createSchedule(timeout);

		return myEntity;
	}

	public static void createSchedule(int delay) {

		System.out.println("creating the schedule with delay: " + delay);
		Callable<Boolean> clearCacheTask = new ClearCacheTask();
		scheduledFuture = executerService.schedule(clearCacheTask, delay,
				TimeUnit.MILLISECONDS);
		System.out.println(scheduledFuture.isDone());

	}

	public static boolean clearCache() {
		System.out.println("clearing the cache");
		cacheItem.clear();
		return true;
	}

	public static void stopScheduledTasks() {
		Runnable scheduledTask = new StopScheduledTask();
		scheduledTask.run();
	}

	private static final class StopScheduledTask implements Runnable {

		@Override
		public void run() {
			System.out.println("Stopping scheduler...");
			scheduledFuture.cancel(DONT_INTERRUPT_IF_RUNNING);
			/*
			 * Note that this Task also performs cleanup, by asking the
			 * scheduler to shutdown gracefully.
			 */
			executerService.shutdown();
		}

	}

	public static final class ClearCacheTask implements Callable<Boolean> {

		@Override
		public Boolean call() throws Exception {
			boolean status = DataCache.clearCache();
			return Boolean.valueOf(status);
		}
	}
}
