package com.talia.coupons.logic;


import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

import com.talia.coupons.interfaces.ICacheManager;

@Component
public class CacheManager implements ICacheManager {

	private Map<Object, Object> map;

	public CacheManager() {
		this.map = new HashMap<Object, Object>();
	}

	public void put(Object key, Object value) {
		this.map.put(key, value);
	}

	public Object get(Object key) {
		return this.map.get(key);
	}
}