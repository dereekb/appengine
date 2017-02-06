package com.dereekb.gae.server.storage.upload.data.image;

import com.dereekb.gae.server.storage.exception.InvalidFileDataException;
import com.dereekb.gae.server.storage.services.images.ImageBytesEditor;
import com.dereekb.gae.server.storage.services.images.ImageBytesEditor.Instance;
import com.dereekb.gae.utilities.data.BytesModifier;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.OutputSettings;

/**
 * Simple {@link BytesModifier} implementation for image data.
 * <p>
 * Takes in bytes, and saves those bytes as a new {@link Image} using an
 * {@link ImageBytesEditor}.
 * </p>
 *
 * @author dereekb
 *
 */
public class ImageBytesTypeModifier implements BytesModifier {

	private ImageBytesEditor editor;

	public ImageBytesTypeModifier(ImagesService.OutputEncoding type) {
		this(100, type);
	}

	public ImageBytesTypeModifier(int quality, ImagesService.OutputEncoding type) {
		OutputSettings settings = new OutputSettings(type);
		settings.setQuality(quality);
		this.editor = new ImageBytesEditor(settings);
	}

	public ImageBytesTypeModifier(OutputSettings settings) {
		this.editor = new ImageBytesEditor(settings);
	}

	// MARK: BytesModifier
	@Override
    public byte[] modifyBytesContent(byte[] bytes) throws InvalidFileDataException {
		Instance instance = this.editor.newInstance(bytes);
		Image newImage = instance.createEditedImage();
		return newImage.getImageData();
	}

	@Override
	public String toString() {
		return "ImageBytesTypeModifier [editor=" + this.editor + "]";
	}

}
