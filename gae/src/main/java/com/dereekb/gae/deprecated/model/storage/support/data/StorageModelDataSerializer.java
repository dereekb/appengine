package com.thevisitcompany.gae.deprecated.model.storage.support.data;

import com.thevisitcompany.gae.deprecated.model.storage.StorageModel;
import com.thevisitcompany.gae.server.storage.download.StoredFileDownloadKeyFactory;
import com.thevisitcompany.gae.server.storage.exception.MissingFileException;

public class StorageModelDataSerializer<T extends StorageModel<T>, D extends StorageModelData> {

	private StoredFileDownloadKeyFactory<T> downloadKeyFactory;

	public StorageModelDataSerializer() {}

	public StorageModelDataSerializer(StoredFileDownloadKeyFactory<T> downloadKeyFactory) {
		this.downloadKeyFactory = downloadKeyFactory;
	}

	protected void addToObject(T object,
	                           D data) {
		object.setItemInfo(data.getItemInfo());
	}

	protected void addAllToData(T object,
	                            D data) {
		this.addToData(object, data);
		data.setItemInfo(object.getItemInfo());
		data.setCreationDate(object.getCreationDate());
	}

	protected void addToData(T object,
	                         D data) {
		data.setId(object.getId());
		this.addDownloadLinkToData(object, data);
	}

	protected void addDownloadLinkToData(T object,
	                                     D data) {
		if (this.downloadKeyFactory != null) {
			String downloadKey = null;

			try {
				downloadKey = this.downloadKeyFactory.makeDownloadKey(object, object);
			} catch (MissingFileException e) {
				data.setMissing(true);
			}

			data.setDownloadKey(downloadKey);
		}
	}

	public StoredFileDownloadKeyFactory<T> getDownloadKeyFactory() {
		return downloadKeyFactory;
	}

	public void setDownloadKeyFactory(StoredFileDownloadKeyFactory<T> downloadKeyFactory) {
		this.downloadKeyFactory = downloadKeyFactory;
	}
}
