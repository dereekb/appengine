package com.thevisitcompany.gae.deprecated.model.storage.image.data.storage;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.visit.models.VisitModelsStoragePathHandler;

public class ImageStoragePathResolver extends VisitModelsStoragePathHandler<Image> {

	@Override
	public String pathForObjectFolder(Image object) {
		return imagePathFormat;
	}

}
