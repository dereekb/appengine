package com.thevisitcompany.gae.deprecated.model.storage.image.data.dto;

import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionDelegate;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.server.storage.download.StoredFileDownloadKeyFactory;
import com.thevisitcompany.gae.utilities.factory.Factory;

public class ImageDataSerializerFactory
        implements Factory<ModelConversionDelegate<Image, ImageData>> {

	private StoredFileDownloadKeyFactory<Image> downloadKeyFactory;

	@Override
	public ModelConversionDelegate<Image, ImageData> make() {
		ImageDataSerializer serializer = new ImageDataSerializer(this.downloadKeyFactory);
		return serializer;
	}

	public StoredFileDownloadKeyFactory<Image> getDownloadKeyFactory() {
		return downloadKeyFactory;
	}

	public void setDownloadKeyFactory(StoredFileDownloadKeyFactory<Image> downloadKeyFactory) {
		this.downloadKeyFactory = downloadKeyFactory;
	}

}
