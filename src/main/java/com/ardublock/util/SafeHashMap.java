package com.ardublock.util;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SafeHashMap<K, V>  extends HashMap<K,V> implements Map<K,V>, Cloneable, Serializable
{
	private static final long serialVersionUID = -731362984994602016L;

	public SafeHashMap()
	{
		super();
	}
	
	@Override
	public synchronized int size()
	{
		return super.size();
	}

	@Override
	public synchronized boolean isEmpty()
	{
		return super.isEmpty();
	}

	@Override
	public synchronized V get(Object key)
	{
		return super.get(key);
	}

	@Override
	public synchronized boolean containsKey(Object key) 
	{
		return super.containsKey(key);
	}

	@Override
	public synchronized V put(K key, V value)
	{
		return super.put(key, value);
	}

	@Override
	public synchronized void putAll(Map<? extends K, ? extends V> m)
	{
		super.putAll(m);
	}

	@Override
	public synchronized V remove(Object key)
	{
		return super.remove(key);
	}

	@Override
	public synchronized void clear()
	{
		super.clear();
	}

	@Override
	public synchronized boolean containsValue(Object value)
	{
		return super.containsValue(value);
	}

	@Override
	public synchronized Set<K> keySet()
	{
		return super.keySet();
	}

	@Override
	public synchronized Collection<V> values()
	{
		return super.values();
	}

	@Override
	public synchronized Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return super.entrySet();
	}

}
