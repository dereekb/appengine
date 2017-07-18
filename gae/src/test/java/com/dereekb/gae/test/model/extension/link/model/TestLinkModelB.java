package com.dereekb.gae.test.model.extension.link.model;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.LongModelKeyConverterImpl;
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
	private Set<Long> parentKeys = new HashSet<Long>();

	/**
	 * Bidirectional link with {@link TestLinkModelA}.
	 */
	private Long mainKey = null;
		
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Set<ModelKey> getParentKeys() {
		return new HashSet<ModelKey>(LongModelKeyConverterImpl.SINGLETON.convert(this.parentKeys));
	}
	
	public void setParentKeys(Set<ModelKey> parentKeys) {
		this.parentKeys = new HashSet<Long>();
		
		if (parentKeys != null) {
			this.parentKeys.addAll(LongModelKeyConverterImpl.SINGLETON.convertFrom(parentKeys));
		}
	}

	public ModelKey getMainKey() {
		return ModelKey.safe(this.mainKey);
	}
	
	public void setMainKey(ModelKey mainKey) {
		this.mainKey = ModelKey.readIdentifier(mainKey);
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
