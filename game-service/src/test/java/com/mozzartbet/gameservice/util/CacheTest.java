package com.mozzartbet.gameservice.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;

public class CacheTest {

	// How to use GuavaCache
	
	@Test
	public void guavaCacheTest() { 
		CacheLoader<String, String> loader;
		loader = new CacheLoader<String, String>() {
			@Override
			public String load(String key) {
				return key.toUpperCase();
			}
		};
		
		LoadingCache<String, String> cache;
		cache = CacheBuilder.newBuilder().
		maximumSize(3).
		build(loader);
		cache.getUnchecked("one");
		cache.getUnchecked("two");
		cache.getUnchecked("three");
		cache.getUnchecked("four");
		assertEquals("HELLO", cache.getUnchecked("hello"));
		assertEquals(3, cache.size());
	}
}
