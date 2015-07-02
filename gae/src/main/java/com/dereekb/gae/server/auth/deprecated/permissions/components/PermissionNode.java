package com.dereekb.gae.server.auth.deprecated.permissions.components;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.utilities.collections.tree.strict.StrictTreeNode;

/**
 * Immutable node that helps generate permission nodes.
 * @author dereekb
 *
 */
public class PermissionNode extends StrictTreeNode<PermissionNode, String> implements PermissionsSet {

	private static final Integer SPLIT_MAX = 2;
	private static final Integer THIS_LEVEL_PERMISSION = 0;
	private static final Integer NEXT_LEVEL_PERMISSION = 1;

	private static final String PERMISSION_BREAK = "\\.";
	private static final String WILDCARD_PERMISSION = "*";
	private static final String PERMISSION_PARENT_FORMAT = "%s.%s";
	
	/**
	 * Lazy-loaded variable used for slightly saving CPU cycles on isEqual() for strings.
	 */
	private Boolean isWildcard = null;
	
	public PermissionNode(String name) {
		super(name);
	}

	public PermissionNode(String name, PermissionNode parent) {
		super(name, parent);
	}
	
	public PermissionNode(String name, PermissionNode parent, Collection<PermissionNode> children) {
		super(name, parent, children);
	}
	
	public PermissionNode cloneNode() {
		return this.cloneNode(null);
	}

	public PermissionNode cloneNode(PermissionNode parent) {
		Collection<PermissionNode> emptyList = Collections.emptyList();
		PermissionNode node = new PermissionNode(this.value, parent, emptyList);
		
		Set<PermissionNode> clonedSubNodes = this.cloneSubnodes(node);
		node.children.addAll(clonedSubNodes);
		
		return node;
	}

	public PermissionNode appendSubnodes(PermissionNode... subNodes) {
		return this.appendSubnodes(null, subNodes);
	}
	
	private PermissionNode appendSubnodes(PermissionNode parent, PermissionNode... subNodes) {
		List<PermissionNode> subNodeList = Arrays.asList(subNodes);
		PermissionNode clone = this.appendSubnodes(parent, subNodeList);
		return clone;
	}

	public PermissionNode appendSubnodes(Collection<PermissionNode> subNodes) {
		return this.appendSubnodes(null, subNodes);
	}
	
	/**
	 * 
	 * @param parent The new parent of the node being built.
	 * @param subNodes
	 * @return
	 */
	private PermissionNode appendSubnodes(PermissionNode parent, Collection<PermissionNode> subNodes) {
		Collection<PermissionNode> emptyList = Collections.emptyList();
		PermissionNode node = new PermissionNode(this.value, parent, emptyList);
		
		Set<PermissionNode> clonedSubNodes = this.cloneSubnodes(node);

		for (PermissionNode children : subNodes) {
			PermissionNode subNodeCopy = children.cloneNode(node);
			clonedSubNodes.add(subNodeCopy);
		}

		node.children.addAll(clonedSubNodes);
		return node;
	}

	public PermissionNode appendSubnodeValues(String... values) {
		return this.appendSubnodeValues(null, values);
	}
	
	private PermissionNode appendSubnodeValues(PermissionNode parent, String... values) {
		Collection<PermissionNode> emptyList = Collections.emptyList();
		PermissionNode node = new PermissionNode(this.value, parent, emptyList);
		
		Set<PermissionNode> clonedSubNodes = this.cloneSubnodes(node);

		for (String value : values) {
			PermissionNode subNodeCopy = new PermissionNode(value, node);
			clonedSubNodes.add(subNodeCopy);
		}

		node.children.addAll(clonedSubNodes);
		return node;
	}
	
	private Set<PermissionNode> cloneSubnodes(PermissionNode parent) {
		Set<PermissionNode> clones = new HashSet<PermissionNode>();
		
		for (PermissionNode children : this.children) {
			PermissionNode clone = children.cloneNode(parent);
			clones.add(clone);
		}
		
		return clones;
	}

	@Override
	public boolean containsPermission(String permission) {
		boolean containsPermission = false;
		
		if (permission != null && (permission.isEmpty() == false)) {
			String[] split = permission.split(PERMISSION_BREAK, SPLIT_MAX);
			String levelPermission = split[THIS_LEVEL_PERMISSION];

			if (this.isWildcardEqual(permission)) {
				containsPermission = true;
			} else {
				if (levelPermission.equals(this.value)) {
					String nextPermission = split[NEXT_LEVEL_PERMISSION];
					containsPermission = this.containsPermissionNodeWithValue(nextPermission);
				}
			}
		}
		
		return containsPermission;
	}
	
	/**
	 * Recursive portion of containsPermission. 
	 * 
	 * Expects that the value of this already exists, since it was picked by it's parent.
	 * 
	 * @param permission Permission of the NEXT LEVEL, 
	 * so if this node's value is "visit" in "visit.place.test.etc.etc.", then permission will be "place.test.etc.etc".
	 * 
	 * This value should never be null.
	 * 
	 * @return
	 */
	private boolean containsPermissionNodeWithValue(String permission) {
		
		boolean containsPermission = false;
		
		if (this.isWildcard()) {
			containsPermission = true;
		} else {
			String[] split = permission.split(PERMISSION_BREAK, SPLIT_MAX);
			String levelPermission = split[THIS_LEVEL_PERMISSION];
			
			PermissionNode childNode = this.getChildWithValueOrWildcard(levelPermission);
			
			if (childNode != null) {
				if (permission.equals(levelPermission)) {
					containsPermission = true;
				} else {
					String nextPermission = split[NEXT_LEVEL_PERMISSION];
					containsPermission = childNode.containsPermissionNodeWithValue(nextPermission);
				}
			} else {
				containsPermission = false;
			}
		}
		
		return containsPermission;
	}

	@Override
	public boolean satisfiesPermission(String permission) {
		boolean satisfiesPermission = false;
		
		if (permission != null && (permission.isEmpty() == false)) {
			String[] split = permission.split(PERMISSION_BREAK, SPLIT_MAX);
			String levelPermission = split[THIS_LEVEL_PERMISSION];

			if (permission.equals(this.value) || levelPermission.equals(WILDCARD_PERMISSION)) {
				satisfiesPermission = true;
			} else {
				if (levelPermission.equals(this.value)) {
					String nextPermission = split[NEXT_LEVEL_PERMISSION];
					satisfiesPermission = this.satisfiesPermissionNodeWithValue(nextPermission);
				}
			}
		}
		
		return satisfiesPermission;
	}
	
	private boolean satisfiesPermissionNodeWithValue(String permission) {
		
		boolean satisfiesPermission = false;
		
		String[] split = permission.split(PERMISSION_BREAK, SPLIT_MAX);
		String levelPermission = split[THIS_LEVEL_PERMISSION];
		
		if (levelPermission.equals(WILDCARD_PERMISSION)) {
			satisfiesPermission = true;
		} else {
			PermissionNode childNode = this.getChildWithValue(levelPermission);
			
			if (childNode != null) {
				if (permission.equals(levelPermission)) {
					satisfiesPermission = true;
				} else {
					String nextPermission = split[NEXT_LEVEL_PERMISSION];
					satisfiesPermission = childNode.satisfiesPermissionNodeWithValue(nextPermission);
				}
			} else {
				satisfiesPermission = false;
			}
		}
		
		return satisfiesPermission;
	}

	private PermissionNode getChildWithValue(String value) {
		PermissionNode childWithValue = null;
		
		for (PermissionNode child : this.children) {
			boolean equalsValue = child.getValue().equals(value);
			if (equalsValue) {
				childWithValue = child;
				break;
			}
		}
		
		return childWithValue;
	}

	private PermissionNode getChildWithValueOrWildcard(String value) {
		PermissionNode childWithValue = null;
		
		for (PermissionNode child : this.children) {
			boolean equalsValue = child.isWildcardEqual(value);
			if (equalsValue) {
				childWithValue = child;
				break;
			}
		}
		
		return childWithValue;
	}
	
	private boolean isWildcard() {
		Boolean isWildcard = this.isWildcard;
		
		if (isWildcard == null) {
			isWildcard = this.value.equals(WILDCARD_PERMISSION);
			this.isWildcard = isWildcard;
		}
		
		return isWildcard;
	}
	
	/**
	 * Specials "isEqual" where if this is a wildcard then 
	 * @param value
	 * @return
	 */
	private boolean isWildcardEqual(String value) {
		boolean isEqual = this.isWildcard() || this.value.equals(value);
		return isEqual;
	}
	
	@Override
	public String toString() {
		boolean hasParent = (parent != null);
		String string = this.value;
		
		if (hasParent) {
			String parentString = this.parent.toString();
			string = String.format(PERMISSION_PARENT_FORMAT, parentString, this.value);
		}
		
		return string;
	}

	public Set<String> allStrings() {
		Set<String> stringSet = new HashSet<String>();
		this.allStrings(stringSet);
		return stringSet;
	}
	
	public void allStrings(Set<String> stringSet) {
		String nodeString = this.toString();
		stringSet.add(nodeString);
		
		for (PermissionNode children : this.children) {
			children.allStrings(stringSet);
		}
	}
	
}
