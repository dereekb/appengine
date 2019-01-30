package com.dereekb.gae.server.storage.upload.data.image.impl;

import com.dereekb.gae.server.storage.services.images.ImageBytesEditor.Instance;
import com.dereekb.gae.server.storage.upload.data.image.ImageBytesModifierDelegate;
import com.google.appengine.api.images.Image;

/**
 * {@link ImageBytesModifierDelegate} implementation.
 *
 * Modifies the input image to meet this element's values.
 *
 * @author dereekb
 */
public class ImageBytesModifierDelegateImpl
        implements ImageBytesModifierDelegate {

	private Integer width;
	private Integer height;
	private Boolean stretch = true;

	public ImageBytesModifierDelegateImpl(Integer width, Integer height, Boolean stretch) {
		this.width = width;
		this.height = height;
		this.stretch = stretch;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Boolean getStretch() {
		return this.stretch;
	}

	public void setStretch(Boolean stretch) {
		this.stretch = stretch;
	}

	// MARK: ImageBytesModifierDelegate
	@Override
	public Image editImage(Instance instance) {

		instance.resizeImage(this.width, this.height, this.stretch);

		return instance.createEditedImage();
	}

	@Override
	public String toString() {
		return "ImageBytesModifierDelegateImpl [width=" + this.width + ", height=" + this.height + ", stretch="
		        + this.stretch + "]";
	}

}
