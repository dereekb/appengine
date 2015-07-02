package com.thevisitcompany.gae.deprecated.model.storage.image.search.document.index;

import com.thevisitcompany.gae.deprecated.model.storage.image.Image;
import com.thevisitcompany.gae.model.extension.search.document.deprecated.builds.AbstractDocumentBuilder;

public class ImageSearchDocumentBuilder extends AbstractDocumentBuilder<Image, Long, ImageSearchDocument> {

	@Override
	public ImageSearchDocument buildDocument(Image object) {
		ImageSearchDocument searchDocument = new ImageSearchDocument(object);
		return searchDocument;
	}

}
