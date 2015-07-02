package com.dereekb.gae.utilities.collections.tree.map.delegates;

import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeBuilderDelegate;
import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeValue;

/**
 * Basic Delegate for constructing String maps.
 * 
 * Ignores Parameters.
 * 
 * @author dereekb
 */
public class StringMapTreeBuilderDelegate
        implements MapTreeBuilderDelegate<String> {

	@Override
	public String stringForValue(String value) {
		return value;
	}

	@Override
	public String valueForString(MapTreeValue treeValue) {
		return treeValue.getValue();
	}

}
