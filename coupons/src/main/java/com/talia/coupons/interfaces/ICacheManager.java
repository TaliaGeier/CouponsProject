package com.talia.coupons.interfaces;

public interface ICacheManager {

	public void put(Object key, Object value);

	public Object get(Object key);

}