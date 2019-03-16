package com.thevisitcompany.gae.deprecated.model.storage;

import javax.validation.constraints.Size;

/**
 * Contains info about a storage item. Used while indexing storage items for search.
 * 
 * @author dereekb
 */
public class StorageModelItemInfo {

	@Size(min = 0, max = 30)
	private String group;

	@Size(min = 0, max = 30)
	private String category;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
