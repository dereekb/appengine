package com.dereekb.gae.test.model.extension.link.model;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.test.model.extension.link.LinkSystemTests;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Model "B" used by {@link LinkSystemTests}.
 * <p>
 * Has a string identifier and links to a parent.
 * 
 * @author dereekb
 *
 */
@Entity
public class TestLinkModelB extends DatabaseModel implements ObjectifyModel<TestLinkModelB> {

	public static final String MODEL_ENTITY_NAME = TestLinkModelB.class.getSimpleName();
	
	private static final long serialVersionUID = 1L;

	@Id
	private String identifier;

	/**
	 * Parent-Child bidirectional link with {@link TestLinkModelA}.
	 */
	private Set<ModelKey> parentKeys = new HashSet<ModelKey>();
		
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Set<ModelKey> getParentKeys() {
		return this.parentKeys;
	}
	
	public void setParentKeys(Set<ModelKey> parentKeys) {
		this.parentKeys = new HashSet<ModelKey>();
		
		if (parentKeys != null) {
			this.parentKeys.addAll(parentKeys);
		}
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readStringKey(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<TestLinkModelB> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(TestLinkModelB.class, this.identifier);
	}

}
