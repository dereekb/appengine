package com.dereekb.gae.server.auth.deprecated.permissions.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Helps in building new nodes. 
 * 
 * Links to the parent of the node being built are unattached since PermissionNodes are immutable, so building from the top down is preferred.
 * @author dereekb
 */
public class PermissionNodeBuilder {

	private PermissionNode currentNode;
	
	public PermissionNodeBuilder(String name) {
		this.currentNode = new PermissionNode(name);
	}
	
	public PermissionNodeBuilder(PermissionNode node) {
		this.currentNode = node.cloneNode();
	}

	public PermissionNodeBuilder cloneBuilder() {
		PermissionNodeBuilder builder = new PermissionNodeBuilder(this.currentNode);
		return builder;
	}
	
	/**
	 * Adds a sub node to the current node.
	 * @param node
	 */
	public void addNodes(String... values) {		
		PermissionNode clonedNode = this.currentNode.appendSubnodeValues(values);
		this.currentNode = clonedNode;
	}
	
	/**
	 * Adds a sub node to the current node.
	 * @param node
	 */
	public void addNodes(PermissionNode... nodes) {
		PermissionNode clonedNode = this.currentNode.appendSubnodes(nodes);
		
		this.currentNode = clonedNode;
	}

	public void addToRoot(String value) {
		PermissionNode parent = new PermissionNode(value);
		PermissionNode appendedParent = parent.appendSubnodes(this.currentNode);
		
		this.currentNode = appendedParent;
	}

	public void clearSubNodes() {
		String value = currentNode.getValue();
		PermissionNode newNode = new PermissionNode(value);
		
		this.currentNode = newNode;
	}
	
	public void rename(String name) {
		PermissionNode newNode = new PermissionNode(name);
		newNode = newNode.appendSubnodes(this.currentNode.getChildren());
		this.currentNode = newNode;
	}

	public PermissionNode build() {
		return this.currentNode.cloneNode();
	}

	public PermissionNode buildWithName(String name) {
		PermissionNode newNode = new PermissionNode(name);
		newNode = newNode.appendSubnodes(this.currentNode.getChildren());
		return newNode;
	}

	public List<PermissionNode> buildCopiesWithNames(String... names) {
		List<PermissionNode> nodes = new ArrayList<PermissionNode>();
		
		for (String name : names) {
			PermissionNode builtNode = this.buildWithName(name);
			nodes.add(builtNode);
		}
		 
		return nodes;
	}
	
}
