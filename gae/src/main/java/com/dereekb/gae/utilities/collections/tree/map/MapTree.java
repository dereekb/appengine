package com.dereekb.gae.utilities.collections.tree.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.tree.map.builder.MapTreeBuilder;

/**
 * A tree that uses a map to keep track of children and values.
 * 
 * Nodes can be set as 'wildcards', which make them include the set of all possible values.
 * 
 * @author dereekb
 * @see {@link MapTreeBuilder} for building a tree from a string.
 */
public final class MapTree<T> {

	private MapTree<T> parent;
	private Map<T, MapTree<T>> children = new HashMap<T, MapTree<T>>();
	private boolean wildcard;
	private T value;

	public MapTree() {
		this(false);
	}

	public MapTree(boolean wildcard) {
		this.value = null;
		this.wildcard = wildcard;
	}

	public MapTree(T value) {
		this.value = value;
	}

	public MapTree(MapTree<T> parent, T value) throws NullPointerException {
		this(parent, value, false);
	}

	public MapTree(MapTree<T> parent, T value, boolean wildcard) throws NullPointerException {
		if (parent == null) {
			throw new NullPointerException("Cannot have a null parent for non-root constructor.");
		}

		this.value = value;
		this.parent = parent;
		this.wildcard = wildcard;
	}

	public boolean isRoot() {
		boolean hasNoParent = (parent == null);
		return (hasNoParent);
	}

	public T getValue() {
		return value;
	}

	public boolean isWildcard() {
		return this.wildcard;
	}

	/**
	 * Sets this as a wildcard value.
	 * 
	 * If wildcard is set to true, all children are cleared from this node.
	 * 
	 * @param wildcard
	 */
	public void setWildcard(boolean wildcard) {
		this.wildcard = wildcard;

		if (wildcard) {
			this.children.clear();
		}
	}

	public MapTree<T> getChild(T value) throws NullPointerException {
		return this.children.get(value);
	}

	public Set<T> getChildrenValues() {
		Set<T> childrenValues = children.keySet();
		return childrenValues;
	}

	public List<MapTree<T>> getChildren() {
		List<MapTree<T>> children = null;

		if (this.children.isEmpty()) {
			children = Collections.emptyList();
		} else {
			Collection<MapTree<T>> treeChildren = this.children.values();
			children = new ArrayList<MapTree<T>>(treeChildren);
		}

		return children;
	}

	/**
	 * Merges the given tree into this one. The value of the opposite tree is ignored while this one is kept.
	 * 
	 * The children that match this one are then merged into each other as well.
	 * 
	 * If the other tree is a wildcard value, then this tree also becomes a wildcard.
	 * 
	 * @param tree
	 */
	public void merge(MapTree<T> tree) {
		if (this.wildcard == false) {
			if (tree.isWildcard()) {
				this.setWildcard(true);
			} else {
				List<MapTree<T>> children = tree.getChildren();

				for (MapTree<T> child : children) {
					this.mergeChild(child);
				}
			}
		}
	}

	/**
	 * Merges a child into this tree.
	 * 
	 * If this is a wildcard, no change occurs.
	 * 
	 * @param child
	 */
	public void mergeChild(MapTree<T> child) {
		if (this.wildcard == false) {
			T value = child.getValue();

			if (this.containsValue(value)) {
				MapTree<T> currentChild = this.children.get(value);
				currentChild.merge(child);
			} else {
				this.put(child);
			}
		}
	}

	/**
	 * Puts a new node into the set of children.
	 * 
	 * If this is a wildcard node, nothing is added.
	 * 
	 * @param value
	 * @return The new node just added to the target, or null if the input value was null, or itself if this is a wildcard node.
	 * @throws NullPointerException
	 */
	public MapTree<T> put(T value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException("Cannot put a null value.");
		}

		MapTree<T> newNode = null;

		if (this.wildcard) {
			newNode = this;
		} else {
			newNode = new MapTree<T>(this, value);
			this.children.put(value, newNode);
		}

		return newNode;
	}

	/**
	 * Adds the given child to the tree. If another child exists with the same value, it is removed.
	 * 
	 * Input with no value (roots) are ignored.
	 * 
	 * @param child
	 * @throws NullPointerException Thrown if the input child is null.
	 */
	public MapTree<T> put(MapTree<T> child) throws NullPointerException {
		if (child == null) {
			throw new NullPointerException("Input child cannot be null.");
		}

		MapTree<T> replacedChild = null;

		if (this.wildcard == false) {
			T value = child.getValue();
			if (value != null) {
				MapTree<T> clonedChild = child.cloneWithParent(this);
				replacedChild = this.children.put(value, clonedChild);
			}
		}

		return replacedChild;
	}

	/**
	 * Adds all of the input children.
	 * 
	 * @param children
	 * @throws NullPointerException
	 */
	public void putAll(Collection<MapTree<T>> children) throws NullPointerException {
		if (children == null) {
			throw new NullPointerException("Children cannot be null.");
		}

		if (this.wildcard == false) {
			for (MapTree<T> child : children) {
				this.put(child);
			}
		}
	}

	/**
	 * Removes a child from this tree.
	 * 
	 * If it exists, the removed child's parent is set to a new root value.
	 * 
	 * @param child
	 * @return The removed child.
	 */
	public MapTree<T> remove(T child) {
		MapTree<T> removedChild = this.children.remove(child);

		if (removedChild != null) {
			removedChild.parent = null;
		}

		return removedChild;
	}

	/**
	 * Returns the amount of children this node has.
	 * 
	 * @return Child count.
	 */
	public int childCount() {
		return children.size();
	}

	public boolean isEmpty() {
		return this.children.isEmpty();
	}

	public boolean containsValue(T value) {
		return (this.wildcard || children.containsKey(value));
	}

	private boolean contains(Collection<T> children) {
		boolean containsAll = true;

		if (this.wildcard) {
			for (T child : children) {
				containsAll = children.contains(child);

				if (containsAll == false) {
					break;
				}
			}
		}

		return containsAll;
	}

	/**
	 * Checks whether or not the input tree is similar to this one.
	 * 
	 * A tree is similar to another if the values of both inputs match eachother, and the children of the input tree are a subset of the
	 * tree.
	 * 
	 * For example:
	 * 'a.b' is similar to 'a.b.c', but not vice-versa.
	 * 
	 * A wildcard value can also be similar: 'a.*'.similarTo('a.b.c.d') will return true.
	 * 
	 * @param tree
	 * @return True if the input tree is contained inside of this one as a child.
	 */
	public boolean similarTo(MapTree<T> tree) {
		boolean containsTree = false;

		if (this.wildcard) {
			containsTree = true;
		} else {
			T thisValue = this.getValue();
			T treeValue = tree.getValue();

			boolean sameValues = false;

			if (thisValue == null && treeValue == null) {
				sameValues = true;
			} else if (thisValue != null && treeValue != null) {
				sameValues = (thisValue.equals(treeValue));
			}

			if (sameValues) {
				containsTree = this.containsChildren(tree);
			}
		}

		return containsTree;
	}

	/**
	 * Checks whether or not the input tree is contained inside of this tree as a child.
	 * 
	 * For example:
	 * The tree formed by 'a.b.c' contains 'b.c', and 'b'.
	 * 
	 * @param tree
	 * @return True if the input tree is contained inside of this one as a child.
	 */
	public boolean contains(MapTree<T> tree) {
		boolean containsTree = false;

		if (this.wildcard) {
			containsTree = true;
		} else {
			T value = tree.getValue();
			MapTree<T> child = this.getChild(value);

			if (child != null) {
				containsTree = child.containsChildren(tree);
			}
		}

		return containsTree;
	}

	/**
	 * Recursively checks that the children of the input tree exist in the subset of this tree.
	 * 
	 * If the input tree is a wildcard, it 'contains' the set of all possible entries. This means this will only contain all those children
	 * if it is also a wildcard.
	 * 
	 * @param tree
	 * @return True if the input tree's children exist in the subset of this tree.
	 * @see containsTree() function to check against the the entire tree.
	 */
	public boolean containsChildren(MapTree<T> tree) {
		boolean containsChildrenTrees = true;

		if (tree.isWildcard()) {
			containsChildrenTrees = this.wildcard;
		} else {
			Set<T> childValues = tree.getChildrenValues();
			boolean containsAllChildValues = this.contains(childValues);

			if (containsAllChildValues) {
				if (childValues.size() > 0) {
					List<MapTree<T>> treeChildren = tree.getChildren();

					for (MapTree<T> treeChild : treeChildren) {
						T value = treeChild.getValue();

						MapTree<T> child = this.children.get(value);
						containsChildrenTrees = (child != null && child.similarTo(treeChild));

						if (containsChildrenTrees == false) {
							break;
						}
					}
				}
			} else {
				containsChildrenTrees = false;
			}
		}

		return containsChildrenTrees;
	}

	/**
	 * Clones the tree at this point, making a new tree with this value as the root.
	 * 
	 * @return
	 */
	public MapTree<T> newTree() {
		MapTree<T> newRoot = new MapTree<T>();
		newRoot.put(this);
		return newRoot;
	}

	private MapTree<T> cloneWithParent(MapTree<T> parent) {
		MapTree<T> clone = new MapTree<T>(parent, this.value);

		if (this.children.isEmpty() == false) {
			for (MapTree<T> child : this.children.values()) {
				clone.put(child);
			}
		}

		return clone;
	}

	private static final String MAP_TREE_STRING_FORMAT = "(%s%s.[%s])";

	@Override
	public String toString() {
		String value = (this.value != null) ? this.value.toString() : "N/A";
		String children = this.children.toString();
		String isWildcard = (this.wildcard) ? "-*" : "";
		String fullString = String.format(MAP_TREE_STRING_FORMAT, value, isWildcard, children);
		return fullString;
	}

}
