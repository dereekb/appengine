package com.dereekb.gae.model.stored.image;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorImpl;
import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.StoredBlobInfoType;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
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
 *
 */
@Cache
@Entity
public final class StoredImage extends SearchableDatabaseModel
        implements ObjectifyModel<StoredImage>, StoredBlobInfoType, Descriptor {

	public static final String DESCRIPTOR_TYPE = "StoredImage";

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
	private Key<StoredBlob> storedBlob;

	/**
	 * (Optional) {@link GeoPlace} entry that places this image into the world.
	 */
	@IgnoreSave(IfNull.class)
	private Key<GeoPlace> geoPlace;

	/**
	 * Set of all {@link StoredImageSet} this image is a part of.
	 */
	@IgnoreSave(IfEmpty.class)
	private Set<Key<StoredImageSet>> imageSets = new HashSet<Key<StoredImageSet>>();

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
		return StoredImageType.typeForId(this.type);
	}

	public void setType(StoredImageType type) {
		this.type = type.getType();
	}

	public Integer getTypeId() {
		return this.type;
	}

	public void setTypeId(Integer type) {
		this.type = type;
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

	public Key<GeoPlace> getGeoPlace() {
		return this.geoPlace;
	}

	public void setGeoPlace(Key<GeoPlace> geoPlace) {
		this.geoPlace = geoPlace;
	}

	public Set<Key<StoredImageSet>> getImageSets() {
		return this.imageSets;
	}

	public void setImageSets(Set<Key<StoredImageSet>> imageSets) {
		if (imageSets == null) {
			imageSets = new HashSet<Key<StoredImageSet>>();
		}

		this.imageSets = imageSets;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
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
		        + ", tags=" + this.tags + ", type=" + this.type + ", storedBlob=" + this.storedBlob + ", geoPlace="
		        + this.geoPlace
		        + ", imageSets=" + this.imageSets + ", searchIdentifier=" + this.searchIdentifier + "]";
	}

}
