package com.thevisitcompany.gae.deprecated.model.storage.support.data;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.KeyedModelData;
import com.thevisitcompany.gae.deprecated.model.storage.StorageModelItemInfo;

@JsonInclude(Include.NON_EMPTY)
public abstract class StorageModelData extends KeyedModelData<Long> {

	private static final long serialVersionUID = 1L;

	@Valid
	private StorageModelItemInfo itemInfo;

	private Integer type;

	private Boolean missing;

	private String downloadKey;

	public StorageModelItemInfo getItemInfo() {
		return itemInfo;
	}

	public void setItemInfo(StorageModelItemInfo info) {
		this.itemInfo = info;
	}

	public String getDownloadKey() {
		return downloadKey;
	}

	public void setDownloadKey(String downloadKey) {
		this.downloadKey = downloadKey;
	}

	public Boolean getMissing() {
		return missing;
	}

	public void setMissing(Boolean missing) {
		this.missing = missing;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
