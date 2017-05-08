package com.dereekb.gae.web.api.model.extension.link.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

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
	@NotEmpty
	@Size(max = 50)
	private Set<String> targetKeys;

	public ApiLinkChangeImpl() {}

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

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public Set<String> getTargetKeys() {
		return this.targetKeys;
	}

	@JsonIgnore
	public void setTargetKeys(Set<String> targetKeys) {
		this.targetKeys = targetKeys;
	}

	public void setTargetKeys(Collection<String> targetKeys) {
		if (targetKeys != null) {
			this.targetKeys = new HashSet<>(targetKeys);
		} else {
			this.targetKeys = null;
		}
	}

	@Override
	public String toString() {
		return "ApiLinkChange [action=" + this.action + ", primaryKey=" + this.primaryKey + ", linkName="
		        + this.linkName + ", targetKeys=" + this.targetKeys + "]";
	}

}
