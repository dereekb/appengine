package com.dereekb.gae.model.stored.blob;

import java.util.Date;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.storage.file.Storable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;
import com.googlecode.objectify.condition.IfNotZero;
import com.googlecode.objectify.condition.IfZero;

/**
 * Represents a reference to a blob that is stored in the systems.
 *
 * @author dereekb
 */
@Cache
@Entity
public final class StoredBlob extends DescribedDatabaseModel
        implements ObjectifyModel<StoredBlob>, Storable {

	private static final long serialVersionUID = 1L;

	private static final String FILE_FORMAT = "%s.%s";

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Date the data was uploaded/created.
	 */
	@Index
	private Date date = new Date();

	/**
	 * Identifier that describes the file's format.
	 *
	 * Described by {@link StoredBlobType}.
	 */
	@Index({ IfNotZero.class, IfNotNull.class })
	@IgnoreSave({ IfZero.class })
	private Integer typeId = StoredBlobType.DEFAULT_TYPE_ID;

	public StoredBlob() {}

	public StoredBlob(Long identifier) {
	    this.identifier = identifier;
    }

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getTypeId() {
		return this.typeId;
	}

	public StoredBlobType getBlobType() {
		return StoredBlobType.typeForId(this.typeId);
	}

	public void setBlobType(StoredBlobType type) {
		this.typeId = type.getId();
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return new ModelKey(this.identifier);
	}

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
	public Key<StoredBlob> getObjectifyKey() {
		return Key.create(StoredBlob.class, this.identifier);
	}

	// Storable
	@Override
    public String getFilename() {
		StoredBlobType type = this.getBlobType();
		String name = this.identifier.toString();
		String ending = type.getEnding();
		return String.format(FILE_FORMAT, name, ending);
    }

	@Override
	public String toString() {
		return "StoredBlob [identifier=" + this.identifier + ", date=" + this.date + ", typeId=" + this.typeId
		        + ", descriptorType=" + this.descriptorType + ", descriptorId=" + this.descriptorId + "]";
	}

}
