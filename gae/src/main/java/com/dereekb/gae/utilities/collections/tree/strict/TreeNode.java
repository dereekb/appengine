package com.dereekb.gae.utilities.collections.tree.strict;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class TreeNode<T, V> {

	protected final V value;
	protected final T parent;
	protected final Set<T> children;

	public TreeNode(V value) {
		this(value, null);
	}

	public TreeNode(V value, T parent) {
		this(value, parent, null);
	}
	
	public TreeNode(V value, T parent, Collection<T> children) {
		this.value = value;
		this.parent = parent;
		
		if (children != null) {
			this.children = new HashSet<T>();
			this.children.addAll(children);
		} else {
			this.children = Collections.emptySet();
		}
	}
	
	public V getValue() {
		return value;
	}

	public T getParent() {
		return parent;
	}

	public Set<T> getChildren() {
		return children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode<?, Object> other = (TreeNode<?, Object>) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
