package com.dereekb.gae.test.model.extension.link.model;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

public class SomeLinkModel
        implements UniqueModel {

	public static final String MODEL_TYPE = "SomeLinkModel";

	private Long identifier;

	public static final String DIFFERENT_MODEL_LINKS_NAME = "Different";

	/**
	 * Links to {@link DifferentLinkModel}.
	 */
	private Set<String> differentModelLinks = new HashSet<String>();

	private Integer saved = 0;

	public SomeLinkModel(ModelKey key) {
		this.identifier = key.getId();
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Set<String> getDifferentModelLinks() {
		return this.differentModelLinks;
	}

	public void setDifferentModelLinks(Set<String> differentModelLinks) {
		this.differentModelLinks = differentModelLinks;
	}

	public void save() {
		this.saved += 1;
	}

	protected Integer getSaved() {
		return this.saved;
	}

	protected void setSaved(Integer saved) {
		this.saved = saved;
	}

	// UniqueModel
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public ModelKey keyValue() {
		return this.getModelKey();
	}

}
