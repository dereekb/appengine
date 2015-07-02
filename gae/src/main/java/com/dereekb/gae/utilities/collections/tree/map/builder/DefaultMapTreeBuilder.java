package com.dereekb.gae.utilities.collections.tree.map.builder;

public class DefaultMapTreeBuilder extends MapTreeBuilder<MapTreeValue>
        implements MapTreeBuilderDelegate<MapTreeValue> {

	public DefaultMapTreeBuilder() {
		super();
		this.setDelegate(this);
	}

	@Override
	public String stringForValue(MapTreeValue value) {
		return value.toString();
	}

	@Override
	public MapTreeValue valueForString(MapTreeValue treeValue) {
		return treeValue;
	}

}
