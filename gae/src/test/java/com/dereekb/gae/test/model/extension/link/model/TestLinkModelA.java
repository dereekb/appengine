package com.dereekb.gae.test.model.extension.link.model;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.datastore.models.DatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.LongModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.models.keys.conversion.impl.StringModelKeyConverterImpl;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.keys.util.ObjectifyKeyUtility;
import com.dereekb.gae.test.model.extension.link.LinkSystemTests;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Model "A" used by {@link LinkSystemTests}.
 * <p>
 * Has a number identifier and links to different children.
 * 
 * @author dereekb
 *
 */
@Entity
public class TestLinkModelA extends DatabaseModel implements ObjectifyModel<TestLinkModelA> {
	
	public static final String MODEL_ENTITY_NAME = TestLinkModelA.class.getSimpleName();

	private static final long serialVersionUID = 1L;

	@Id
	private Long identifier;

	/**
	 * Parent-Child bidirectional link with {@link TestLinkModelA}.
	 */
	private Long primaryKey;

	/**
	 * Parent-Child bidirectional link with {@link TestLinkModelA}.
	 */
	private Set<Long> aChildKeys = new HashSet<Long>();
	
	/**
	 * One-way link with {@link TestLinkModelB}.
	 */
	private String secondaryKey;
	
	/**
	 * Parent-Child bidirectional link with {@link TestLinkModelB}.
	 */
	private Set<String> bChildKeys = new HashSet<String>();

	/**
	 * Bidirectional link with a {@link TestLinkModelB}.
	 */
	private String thirdKey;
	
	public Long getIdentifier() {
		return this.identifier;
	}
	
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	
	public ModelKey getPrimaryKey() {
		return ModelKey.safe(this.primaryKey);
	}
	
	public void setPrimaryKey(ModelKey primaryKey) {
		this.primaryKey = ModelKey.readIdentifier(primaryKey);
	}

	public Set<ModelKey> getaChildKeys() {
		return new HashSet<ModelKey>(LongModelKeyConverterImpl.SINGLETON.convert(this.aChildKeys));
	}

	public void setaChildKeys(Set<ModelKey> aChildKeys) {
		this.aChildKeys = new HashSet<Long>();
		
		if (aChildKeys != null) {
			this.aChildKeys.addAll(LongModelKeyConverterImpl.SINGLETON.convertFrom(aChildKeys));
		}
	}

	public ModelKey getSecondaryKey() {
		return ModelKey.safe(this.secondaryKey);
	}
	
	public void setSecondaryKey(ModelKey secondaryKey) {
		this.secondaryKey = ModelKey.readName(secondaryKey);
	}

	public Set<ModelKey> getbChildKeys() {
		return new HashSet<ModelKey>(StringModelKeyConverterImpl.CONVERTER.convert(this.bChildKeys));
	}

	public void setbChildKeys(Set<ModelKey> bChildKeys) {
		this.bChildKeys = new HashSet<String>();
		
		if (bChildKeys != null) {
			this.bChildKeys.addAll(StringModelKeyConverterImpl.CONVERTER.convertFrom(bChildKeys));
		}
	}

	public ModelKey getThirdKey() {
		return ModelKey.safe(this.thirdKey);
	}
	
	public void setThirdKey(ModelKey thirdKey) {
		this.thirdKey = ModelKey.readName(thirdKey);
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	@Override
	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readIdentifier(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<TestLinkModelA> getObjectifyKey() {
		return ObjectifyKeyUtility.createKey(TestLinkModelA.class, this.identifier);
	}

}
