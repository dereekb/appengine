package com.dereekb.gae.web.api.model.extension.link;

import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * DTO for {@link ApiLinkChangeConverter}.
 *
 * @author dereekb
 *
 */
public class ApiLinkChange {

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
	@Max(50)
	private Set<String> targetKeys;

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

	public void setTargetKeys(Set<String> targetKeys) {
		this.targetKeys = targetKeys;
	}

	@Override
	public String toString() {
		return "ApiLinkChange [action=" + this.action + ", primaryKey=" + this.primaryKey + ", linkName="
		        + this.linkName + ", targetKeys=" + this.targetKeys + "]";
	}

}
