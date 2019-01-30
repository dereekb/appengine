package com.thevisitcompany.gae.deprecated.model.storage.image.functions;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.model.crud.function.delegate.CreateFunctionDelegate;
import com.thevisitcompany.gae.model.crud.function.delegate.UpdateFunctionDelegate;
import com.thevisitcompany.gae.model.general.geo.Point;
import com.thevisitcompany.gae.server.storage.file.StorageFileInfo;
import com.thevisitcompany.gae.server.storage.upload.function.UploadFunctionDelegate;

public class ImageFunctionsDelegate
        implements CreateFunctionDelegate<Image>, UpdateFunctionDelegate<Image>, UploadFunctionDelegate<Image> {

	@Override
	public boolean update(Image source,
	                      Image context) {
		String name = source.getName();
		String summary = source.getSummary();
		Point location = source.getLocation();

		context.setName(name);
		context.setSummary(summary);
		context.setLocation(location);
		return true;
	}

	@Override
	public Image create(Image source) {
		Image image = new Image();
		return image;
	}

	@Override
	public Image createForUpload(StorageFileInfo source) {
		Image image = new Image();
		image.setName(source.getFilename());
		return image;
	}

}
