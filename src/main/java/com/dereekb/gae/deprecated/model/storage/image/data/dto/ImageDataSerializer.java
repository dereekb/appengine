package com.thevisitcompany.gae.deprecated.model.storage.image.data.dto;

import com.thevisitcompany.gae.deprecated.model.mod.data.SerializerPair;
import com.thevisitcompany.gae.deprecated.model.mod.data.VisitDefaultDataNames;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionDelegate;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionFunction;
import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.deprecated.model.storage.support.data.StorageModelDataSerializer;
import com.thevisitcompany.gae.server.storage.download.StoredFileDownloadKeyFactory;

public class ImageDataSerializer extends StorageModelDataSerializer<Image, ImageData>
        implements ModelConversionDelegate<Image, ImageData> {

	public ImageDataSerializer() {}

	public ImageDataSerializer(StoredFileDownloadKeyFactory<Image> downloadKeyFactory) {
		super(downloadKeyFactory);
	}

	@Override
	public Image convertDataToObject(ImageData data) {
		Image image = new Image(data.getId());
		super.addToObject(image, data);

		image.setName(data.getName());
		image.setSummary(data.getSummary());
		image.setLocation(data.getLocation());

		return image;
	}

	@ModelConversionFunction(isDefault = true, value = { ModelConversionDelegate.DEFAULT_DATA_NAME })
	public boolean convertToArchive(SerializerPair<Image, ImageData> pair) {

		Image object = pair.getSource();
		ImageData archive = new ImageData();
		super.addToData(object, archive);

		archive.setName(object.getName());
		archive.setSummary(object.getSummary());
		archive.setLocation(object.getLocation());

		pair.setResult(archive);
		return true;
	}

	@ModelConversionFunction(value = { VisitDefaultDataNames.FULL_DATA_NAME })
	public boolean convertToFullArchive(SerializerPair<Image, ImageData> pair) {

		Image object = pair.getSource();
		ImageData archive = new ImageData();
		super.addAllToData(object, archive);

		archive.setType(object.getType());
		archive.setName(object.getName());
		archive.setSummary(object.getSummary());
		archive.setLocation(object.getLocation());

		pair.setResult(archive);
		return true;
	}

	@ModelConversionFunction(value = { VisitDefaultDataNames.LINKS_DATA_NAME })
	public boolean convertToLinksArchive(SerializerPair<Image, ImageData> pair) {
		Image object = pair.getSource();
		ImageData archive = new ImageData();
		super.addDownloadLinkToData(object, archive);

		archive.setId(object.getId());
		pair.setResult(archive);
		return true;
	}

}
