package com.dereekb.gae.server.storage.services.data.impl;

import com.dereekb.gae.server.storage.services.images.ImageBytesEditor.ImageEditingInstance;
import com.google.appengine.api.images.Image;

/**
 * {@link ImageBytesModifier} delegate used to perform the transformations.
 *
 * @author dereekb
 *
 */
public interface ImageBytesModifierDelegate {

	/**
	 * Edits an image using the input {@link ImageEditingInstance}.
	 *
	 * @param instance
	 *            {@link ImageEditingIstance}. Never {@code null}.
	 * @return {@link Image}
	 */
	public Image editImage(ImageEditingInstance instance);

}
