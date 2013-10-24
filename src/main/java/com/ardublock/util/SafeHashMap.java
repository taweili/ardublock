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
	
	public synchronized int size()
	{
		return super.size();
	}

	public synchronized boolean isEmpty()
	{
		return super.isEmpty();
	}

	public synchronized V get(Object key)
	{
		return super.get(key);
	}

	public synchronized boolean containsKey(Object key) 
	{
		return super.containsKey(key);
	}

	public synchronized V put(K key, V value)
	{
		return super.put(key, value);
	}

	public synchronized void putAll(Map<? extends K, ? extends V> m)
	{
		super.putAll(m);
	}

	public synchronized V remove(Object key)
	{
		return super.remove(key);
	}

	public synchronized void clear()
	{
		super.clear();
	}

	public synchronized boolean containsValue(Object value)
	{
		return super.containsValue(value);
	}

	public synchronized Set<K> keySet()
	{
		return super.keySet();
	}

	public synchronized Collection<V> values()
	{
		return super.values();
	}

	public synchronized Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return super.entrySet();
	}

}
