package com.dereekb.gae.model.stored.blob;

import java.util.Date;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.storage.object.file.Storable;
import com.dereekb.gae.server.storage.object.file.StorableFile;
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
 * Represents a reference to a blob that is stored in the systems.
 *
 * @author dereekb
 */
@Cache
@Entity
public final class StoredBlob extends DescribedDatabaseModel
        implements ObjectifyModel<StoredBlob>, Storable, StorableFile {

	private static final long serialVersionUID = 1L;

	private static final String FILE_FORMAT = "%s.%s";

	public static final Integer DEFAULT_BLOB_TYPE = StoredBlobType.DEFAULT_TYPE_ID;

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
	@Index({ IfNotDefault.class, IfNotNull.class })
	@IgnoreSave({ IfDefault.class })
	protected Integer type = DEFAULT_BLOB_TYPE;

	/**
	 * The blob's custom filename. Should not include the type ending.
	 * <p>
	 * If {{@link #blobName} is {@code null}, the {{@link #getBlobName()} value
	 * will return this blob's identifier.
	 * </p>
	 */
	private String blobName;

	/**
	 * The full file path.
	 * <p>
	 * Should never be {@code null} for {@link StoredBlob} values saved to the
	 * database.
	 * </p>
	 */
	@IgnoreSave({ IfEmpty.class })
	private String filePath;

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
		return this.type;
	}

	public StoredBlobType getBlobType() {
		return StoredBlobType.valueOf(this.type);
	}

	public void setBlobType(StoredBlobType type) {
		if (type == null) {
			this.setTypeId(null);
		} else {
			this.type = type.getId();
		}
	}

	public void setTypeId(Integer typeId) {
		if (typeId == null) {
			this.type = StoredBlob.DEFAULT_BLOB_TYPE;
		} else {
			this.type = StoredBlobType.valueOf(typeId).id;
		}
	}

	public String getBlobName() {
		return this.blobName;
	}

	public void setBlobName(String blobName) {
		this.blobName = blobName;
	}

	@Override
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
		return "StoredBlob [identifier=" + this.identifier + ", date=" + this.date + ", type=" + this.type
		        + ", descriptorType=" + this.descriptorType + ", descriptorId=" + this.descriptorId + "]";
	}

}
