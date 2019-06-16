package com.dereekb.gae.model.stored.image;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorImpl;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.owner.OwnedDatabaseModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfDefault;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotDefault;
import com.googlecode.objectify.condition.IfNotNull;

/**
 * Is an {@link StoredBlobInfoType} for a {@link StoredBlob}.
 *
 * @author dereekb
 *
 */
@Cache
@Entity
public final class StoredImage extends OwnedDatabaseModel
        implements ObjectifyModel<StoredImage>, StoredBlobInfoType, Descriptor {

	public static final String DESCRIPTOR_TYPE = "StoredImage";

	private static final long serialVersionUID = 1L;

	public static final Integer DEFAULT_IMAGE_TYPE = StoredImageType.IMAGE.id;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * (Optional) Name attached to this image.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String name;

	/**
	 * (Optional) Summary description for this image.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String summary;

	/**
	 * (Optional) Used as search tags.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String tags;

	/**
	 * Describes the image type.
	 */
	@Index({ IfNotDefault.class, IfNotNull.class })
	@IgnoreSave({ IfDefault.class })
	protected Integer type = DEFAULT_IMAGE_TYPE;

	/**
	 * Must be set.
	 *
	 * Key to the {@link StoredBlob} that this image describes.
	 */
	private Key<StoredBlob> storedBlob;

	public StoredImage() {}

	public StoredImage(Key<StoredBlob> storedBlob) {
		this.storedBlob = storedBlob;
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public StoredImageType getType() {
		return StoredImageType.valueOf(this.type);
	}

	public void setType(StoredImageType type) {
		if (type == null) {
			this.setTypeId(null);
		} else {
			this.type = type.getId();
		}
	}

	public Integer getTypeId() {
		return this.type;
	}

	public void setTypeId(Integer type) {
		if (type == null) {
			this.type = DEFAULT_IMAGE_TYPE;
		} else {
			this.type = type;
		}
	}

	public Key<StoredBlob> getStoredBlob() {
		return this.storedBlob;
	}

	public void setStoredBlob(Key<StoredBlob> storedBlob) {
		this.storedBlob = storedBlob;
	}

	@Override
	public ModelKey getStoredBlobKey() {
		return new ModelKey(this.storedBlob.getId());
	}

	public Long getBlobId() {
		Long id = null;

		if (this.storedBlob != null) {
			id = this.storedBlob.getId();
		}

		return id;
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
	public Key<StoredImage> getObjectifyKey() {
		return Key.create(StoredImage.class, this.identifier);
	}

	// Descriptor
	@Override
	public String getDescriptorType() {
		return DESCRIPTOR_TYPE;
	}

	@Override
	public String getDescriptorId() {
		return this.identifier.toString();
	}

	@Override
	public boolean equals(Descriptor descriptor) {
		return DescriptorImpl.descriptorsAreEqual(this, descriptor);
	}

	@Override
	public String toString() {
		return "StoredImage [identifier=" + this.identifier + ", name=" + this.name + ", summary=" + this.summary
		        + ", tags=" + this.tags + ", type=" + this.type + ", storedBlob=" + this.storedBlob
		        + "]";
	}

}
