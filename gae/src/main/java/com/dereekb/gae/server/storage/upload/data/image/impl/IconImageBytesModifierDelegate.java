package com.dereekb.gae.server.storage.upload.data.image.impl;

import com.dereekb.gae.server.storage.services.images.ImageBytesEditor.Instance;
import com.dereekb.gae.server.storage.upload.data.image.ImageBytesModifierDelegate;
import com.google.appengine.api.images.Image;


public class IconImageBytesModifierDelegate
        implements ImageBytesModifierDelegate {

	private Integer size;
	private Boolean stretch = true;

	@Override
	public Image editImage(Instance instance) {

		instance.resizeImage(this.size, this.size, this.stretch);

		return instance.createEditedImage();
	}

}
