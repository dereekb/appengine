package com.dereekb.gae.model.stored.image.set;

import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.model.extension.search.document.search.SearchableDatabaseModel;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotEmpty;
import com.googlecode.objectify.condition.IfNull;

/**
 * A gallery that contains a set of {@link StoredImage} references.
 *
 * @author dereekb
 *
 */
@Cache
@Entity
public final class StoredImageSet extends SearchableDatabaseModel
        implements ObjectifyModel<StoredImageSet> {

	private static final long serialVersionUID = 1L;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Gallery name/label.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String label;

	/**
	 * Gallery information.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String detail;

	/**
	 * (Optional) Tags the content of this gallery.
	 */
	@IgnoreSave({ IfEmpty.class })
	private String tags;

	/**
	 * (Optional) Icon for this gallery.
	 */
	@IgnoreSave({ IfNull.class })
	private Key<StoredImage> icon;

	/**
	 * {@link Image} in this gallery.
	 * 
	 * Images are indexed to allow querying all image sets that contain a given
	 * image, at a later time.
	 */
	@Index({ IfNotEmpty.class })
	@IgnoreSave({ IfEmpty.class })
	private Set<Key<StoredImage>> images = new HashSet<Key<StoredImage>>();

	public StoredImageSet() {}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getTags() {
		return this.tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Key<StoredImage> getIcon() {
		return this.icon;
	}

	public void setIcon(Key<StoredImage> icon) {
		this.icon = icon;
	}

	public Set<Key<StoredImage>> getImages() {
		return this.images;
	}

	public void setImages(Set<Key<StoredImage>> images) {
		if (images == null) {
			images = new HashSet<Key<StoredImage>>();
		}

		this.images = images;
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
	public Key<StoredImageSet> getObjectifyKey() {
		return Key.create(StoredImageSet.class, this.identifier);
	}

	@Override
	public String toString() {
		return "StoredImageSet [identifier=" + this.identifier + ", label=" + this.label + ", detail=" + this.detail
		        + ", tags=" + this.tags + ", images=" + this.images + "]";
	}

}
