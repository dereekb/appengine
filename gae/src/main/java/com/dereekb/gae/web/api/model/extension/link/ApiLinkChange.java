package com.dereekb.gae.web.api.model.extension.link;

import java.util.Set;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChangeType;

/**
 * DTO for {@link ApiLinkChangeConverter}.
 *
 * @author dereekb
 *
 */
public interface ApiLinkChange {
	
	/**
	 * Returns the optional id of the change.
	 * 
	 * @return {@link String}. Can be {@code null}.
	 */
	public String getId();

	/**
	 * Returns the string name of the {@link MutableLinkChangeType}.
	 * 
	 * @return {@link string}. Never {@code null}.
	 */
	public String getAction();

	/**
	 * Returns the primary model key as a string.
	 * 
	 * @return {@link string}. Never {@code null}.
	 */
	public String getPrimaryKey();

	/**
	 * Returns the target link name.
	 * 
	 * @return {@link string}. Never {@code null}.
	 */
	public String getLinkName();

	/**
	 * Returns the target keys as a set.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<String> getTargetKeys();

}
