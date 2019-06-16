package com.dereekb.gae.server.storage.upload.data.image;

import com.dereekb.gae.server.deprecated.storage.exception.InvalidFileDataException;
import com.dereekb.gae.server.deprecated.storage.services.images.ImageBytesEditor;
import com.dereekb.gae.utilities.data.BytesModifier;
import com.google.appengine.api.images.Image;

/**
 * {@link BytesModifier} for modifying {@code byte[]} data for an image.
 *
 * @author dereekb
 *
 */
public class ImageBytesModifier
        implements BytesModifier {

	private ImageBytesEditor editor;
	private ImageBytesModifierDelegate delegate;

	public ImageBytesModifier() {}

	public ImageBytesModifier(ImageBytesEditor editor, ImageBytesModifierDelegate delegate) {
		this.editor = editor;
		this.delegate = delegate;
	}

	public ImageBytesEditor getEditor() {
		return this.editor;
	}

	public void setEditor(ImageBytesEditor editor) {
		this.editor = editor;
	}

	public ImageBytesModifierDelegate getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ImageBytesModifierDelegate delegate) {
		this.delegate = delegate;
	}

	// MARK: BytesModifier
	@Override
	public byte[] modifyBytesContent(byte[] bytes) throws InvalidFileDataException {
		ImageBytesEditor.Instance instance = this.editor.newInstance(bytes);
		Image image = this.delegate.editImage(instance);
		return image.getImageData();
	}

	@Override
	public String toString() {
		return "ImageBytesModifier [editor=" + this.editor + ", delegate=" + this.delegate + "]";
	}

}
