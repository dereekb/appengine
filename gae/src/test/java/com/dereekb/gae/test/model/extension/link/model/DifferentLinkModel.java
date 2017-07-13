package com.dereekb.gae.test.model.extension.link.model;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * A generic model to use for link testing.
 *
 * @author dereekb
 *
 */
@Deprecated
public class DifferentLinkModel
        implements UniqueModel {

	public static final String MODEL_TYPE = "DifferentLinkModel";

	private String name;

	public static final String SOME_MODEL_LINKS_NAME = "Some";

	/**
	 * Links to {@link SomeLinkModel}.
	 */
	private Set<Long> someModelLinks = new HashSet<Long>();

	private Integer saved = 0;

	public DifferentLinkModel(ModelKey key) {
		this.name = key.getName();
	}

	protected String getName() {
		return this.name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	public Set<Long> getSomeModelLinks() {
		return this.someModelLinks;
	}

	public void setSomeModelLinks(Set<Long> someModelLinks) {
		this.someModelLinks = someModelLinks;
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
		return new ModelKey(this.name);
	}

	// MARK: Keyed
	@Override
	public ModelKey keyValue() {
		return this.getModelKey();
	}

}
