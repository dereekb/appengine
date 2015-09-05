package com.dereekb.gae.server.storage.services.data.impl;

import com.dereekb.gae.server.storage.exception.InvalidFileDataException;
import com.dereekb.gae.server.storage.services.data.BytesContentModifier;
import com.dereekb.gae.server.storage.services.images.ImageBytesEditor;
import com.dereekb.gae.server.storage.services.images.ImageBytesEditor.ImageEditingInstance;
import com.google.appengine.api.images.Image;

/**
 * {@link BytesContentModifier} for modifying {@code byte[]} data for an image.
 *
 * @author dereekb
 *
 */
public class ImageBytesModifier
        implements BytesContentModifier {

	private ImageBytesEditor editor;
	private ImageBytesModifierDelegate delegate;

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

	// MARK: BytesContentModifier
	@Override
	public byte[] modifyBytesContent(byte[] bytes) throws InvalidFileDataException {
		ImageEditingInstance instance = this.editor.newInstance(bytes);
		Image image = this.delegate.editImage(instance);
		return image.getImageData();
	}

	@Override
	public String toString() {
		return "ImageBytesModifier [editor=" + this.editor + ", delegate=" + this.delegate + "]";
	}

}
