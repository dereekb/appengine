package com.dereekb.gae.utilities.collections.map.catchmap;


/**
 * Special map that has default values.
 *
 * @author dereekb
 *
 * @param <K>
 *            key type
 * @param <T>
 *            element type
 */
public interface CatchMap<K, T> {

	public T get(K key);

	public T getCatchAll();

	public void setCatchAll(T catchall);

	public K getNullKeyValue();

	public void setNullKeyValue(K key);

}
