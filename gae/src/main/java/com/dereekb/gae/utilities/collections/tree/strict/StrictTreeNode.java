package com.dereekb.gae.utilities.collections.tree.strict;

import java.util.Collection;

public abstract class StrictTreeNode<T extends StrictTreeNode<T, V>, V> extends TreeNode<T, V> {

	public StrictTreeNode(V value) {
		super(value);
	}
	
	public StrictTreeNode(V value, T parent) {
		super(value, parent);
	}
	
	public StrictTreeNode(V value, T parent, Collection<T> children) {
		super(value, parent, children);
	}

	/**
	 * Returns the size of this tree, including the root.
	 * @return
	 */
	public Integer size() {
		Integer size = 1 + this.childrenSize();
		return size;
	}
	
	/**
	 * Returns the size of all combined children.
	 * @return
	 */
	public Integer childrenSize() {
		Integer size = 0;
		
		for (StrictTreeNode<T,V> child : this.children) {
			Integer childSize = child.size();
			size += childSize;
		}
		
		return size;
	}
	
	
}
