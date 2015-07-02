package com.thevisitcompany.gae.deprecated.model.storage.image;

import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModelRegistry;

public final class ImageRegistry extends ObjectifyModelRegistry<Image> {

	public static ImageRegistry getRegistry() {
		return new ImageRegistry();
	}

	public ImageRegistry() {
		super(Image.class);
	}

}
