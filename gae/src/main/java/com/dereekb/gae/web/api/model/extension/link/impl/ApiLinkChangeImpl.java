package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dereekb.gae.web.api.model.extension.link.ApiLinkChange;
import com.dereekb.gae.web.api.model.extension.link.ApiLinkChangeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * DTO for {@link ApiLinkChangeConverter}.
 *
 * @author dereekb
 *
 */
public class ApiLinkChangeImpl
        implements ApiLinkChange {

	/**
	 * Action to perform.
	 */
	@NotNull
	private String action;

	/**
	 * Keys to change.
	 */
	@NotNull
	private String primaryKey;

	/**
	 * The name of the link to change.
	 */
	@NotNull
	private String linkName;

	/**
	 * Keys of the target model.
	 *
	 * A max of 50 target keys are allowed to be changed at one time.
	 */
	// @NotEmpty // A "clear" request will have no target keys provided.
	@Size(max = 50)
	private Set<String> targetKeys;

	public ApiLinkChangeImpl() {}

	public ApiLinkChangeImpl(ApiLinkChange change) throws NullPointerException {
		this(change.getAction(), change.getPrimaryKey(), change.getLinkName(), change.getTargetKeys());
	}

	protected ApiLinkChangeImpl(String action, String primaryKey, String linkName) {
		this.setAction(action);
		this.setPrimaryKey(primaryKey);
		this.setLinkName(linkName);
	}

	public ApiLinkChangeImpl(String action, String primaryKey, String linkName, String singleTargetKey) {
		this(action, primaryKey, linkName);

		Set<String> targetKeys = new HashSet<>();
		targetKeys.add(singleTargetKey);
		this.setTargetKeys(targetKeys);
	}

	public ApiLinkChangeImpl(String action, String primaryKey, String linkName, Set<String> targetKeys) {
		this(action, primaryKey, linkName);
		this.setTargetKeys(targetKeys);
	}

	@Override
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	@Override
	public Set<String> getTargetKeys() {
		return this.targetKeys;
	}

	public void setTargetKeys(Set<String> targetKeys) {
		this.targetKeys = targetKeys;
	}

	@JsonIgnore
	public void setTargetKeysWithCollection(Collection<String> targetKeys) {
		if (targetKeys != null) {
			this.targetKeys = new HashSet<>(targetKeys);
		} else {
			this.targetKeys = null;
		}
	}

	// MARK: Utility
	public static List<ApiLinkChangeImpl> makeFromChanges(Iterable<? extends ApiLinkChange> changes) {
		List<ApiLinkChangeImpl> newChanges = new ArrayList<ApiLinkChangeImpl>();

		for (ApiLinkChange change : changes) {
			newChanges.add(new ApiLinkChangeImpl(change));
		}

		return newChanges;
	}

	@Override
	public String toString() {
		return "ApiLinkChange [action=" + this.action + ", primaryKey=" + this.primaryKey + ", linkName="
		        + this.linkName + ", targetKeys=" + this.targetKeys + "]";
	}

}
