package com.dereekb.gae.utilities.collections.tree.map.builder;


/**
 * Used by a {@link MapTreeBuilder} to help build a tree.
 * 
 * @author dereekb
 */
public interface MapTreeBuilderDelegate<T> {

	public String stringForValue(T value);

	public T valueForString(MapTreeValue treeValue);

}
