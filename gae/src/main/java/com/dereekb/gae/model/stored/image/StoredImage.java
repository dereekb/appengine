package com.dereekb.gae.model.stored.image;

import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotNull;
import com.googlecode.objectify.condition.IfNotZero;
import com.googlecode.objectify.condition.IfNull;
import com.googlecode.objectify.condition.IfZero;

/**
 * Is an {@link StoredBlobInfoType} for a {@link StoredBlob}.
 *
 * @author dereekb
 */
@Cache
@Entity
public final class StoredImage extends SearchableDatabaseModel
        implements ObjectifyModel<StoredImage>, StoredBlobInfoType {

	private static final long serialVersionUID = 1L;

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
	@Index({ IfNotZero.class, IfNotNull.class })
	@IgnoreSave({ IfZero.class })
	protected Integer type = 0;

	/**
	 * Must be set.
	 *
	 * Key to the {@link StoredBlob} that this image describes.
	 */
	private Key<StoredBlob> blob;

	/**
	 * (Optional) {@link GeoPlace} entry that places this image into the world.
	 */
	@IgnoreSave(IfNull.class)
	private Key<GeoPlace> place;

	public StoredImage() {}

	public StoredImage(Key<StoredBlob> blob) {
		this.blob = blob;
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

	public Integer getTypeId() {
		return this.type;
	}

	public StoredImageType getType() {
		return StoredImageType.typeForId(this.type);
	}

	public void setType(StoredImageType type) {
		this.type = type.getType();
	}

	public Key<StoredBlob> getBlob() {
		return this.blob;
	}

	public void setBlob(Key<StoredBlob> blob) {
		this.blob = blob;
	}

	@Override
	public ModelKey getStoredBlobKey() {
		return new ModelKey(this.blob.getId());
	}

	public Long getBlobId() {
		Long id = null;

		if (this.blob != null) {
			id = this.blob.getId();
		}

		return id;
	}

	public Key<GeoPlace> getPlace() {
		return this.place;
	}

	public Long getPlaceId() {
		Long id = null;

		if (this.place != null) {
			id = this.place.getId();
		}

		return id;
	}

	public void setPlace(Key<GeoPlace> point) {
		this.place = point;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return new ModelKey(this.identifier);
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

	@Override
	public String toString() {
		return "StoredImage [identifier=" + this.identifier + ", name=" + this.name + ", summary=" + this.summary
		        + ", tags=" + this.tags + ", type=" + this.type + ", blob=" + this.blob + ", point=" + this.place + "]";
	}

}
