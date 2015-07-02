package com.thevisitcompany.gae.deprecated.model.storage;

import java.util.Date;

import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfEmpty;
import com.googlecode.objectify.condition.IfNotNull;
import com.googlecode.objectify.condition.IfNotZero;
import com.googlecode.objectify.condition.IfNull;
import com.googlecode.objectify.condition.IfZero;
import com.thevisitcompany.gae.deprecated.model.mod.search.SearchableObjectifyModel;
import com.thevisitcompany.gae.model.extension.objectify.annotation.IfNew;
import com.thevisitcompany.gae.server.storage.file.Storable;
import com.thevisitcompany.gae.server.storage.gcs.container.StorageFileReference;

public abstract class StorageModel<T> extends SearchableObjectifyModel<T>
        implements Storable {

	private static final long serialVersionUID = 1L;

	@IgnoreSave({ IfNull.class, IfNew.class })
	protected StorageModelItemInfo itemInfo = new StorageModelItemInfo();

	@Index({ IfNotZero.class, IfNotNull.class })
	@IgnoreSave({ IfZero.class })
	protected Integer type = 0;

	@IgnoreSave(IfEmpty.class)
	protected String name = "";

	@IgnoreSave(IfEmpty.class)
	protected String summary = "";

	@Index
	protected Date creationDate = new Date();

	public StorageModel() {}

	public StorageModel(Long identifier) {
		super(identifier);
	}

	public StorageFileReference getFile() {
		StorageFileReference file = new StorageFileReference(this.getFilename());
		return file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		if (type == null) {
			type = 0;
		}

		this.type = type;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		if (creationDate == null) {
			creationDate = new Date();
		}

		this.creationDate = creationDate;
	}

	public StorageModelItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(StorageModelItemInfo info) {
		this.itemInfo = info;
	}

}
