package com.dereekb.gae.model.extension.links.components.impl;

import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * Default implementation of {@link LinkTarget}.
 *
 * @author dereekb
 *
 */
public class LinkTargetImpl
        implements LinkTarget {

	private String targetType;
	private ModelKeyType targetKeyType;

	public LinkTargetImpl(String targetType, ModelKeyType targetKeyType) {
		this.targetType = targetType;
		this.targetKeyType = targetKeyType;
	}

	@Override
	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		if (targetType == null) {
			throw new IllegalArgumentException("Link type cannot be null.");
		}

		this.targetType = targetType;
	}

	@Override
	public ModelKeyType getTargetKeyType() {
		return this.targetKeyType;
	}

	public void setTargetKeyType(ModelKeyType targetKeyType) {
		this.targetKeyType = targetKeyType;
	}

	@Override
	public String toString() {
		return "LinkTargetImpl [targetType=" + this.targetType + ", targetKeyType=" + this.targetKeyType + "]";
	}

}
