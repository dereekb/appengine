package com.dereekb.gae.server.storage.services.data.impl;

import com.dereekb.gae.server.storage.services.images.ImageBytesEditor.ImageEditingInstance;

/**
 * {@link ImageBytesModifier} delegate used to perform the transformations.
 *
 * @author dereekb
 *
 */
public interface ImageBytesModifierDelegate {

	/**
	 * Edits an image using the input {@linl ImageEditingInstance}.
	 *
	 * @param instance
	 *            {@link ImageEditingIstance}. Never {@code null}.
	 * @return
	 */
	public Image editImage(ImageEditingInstance instance);

}
