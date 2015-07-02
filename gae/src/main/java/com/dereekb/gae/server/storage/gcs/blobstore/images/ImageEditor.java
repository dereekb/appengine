package com.dereekb.gae.server.storage.gcs.blobstore.images;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.OutputSettings;
import com.google.appengine.api.images.Transform;

/**
 * Helper function for editing images using the GAE Images API.
 * 
 * Default output settings output images as PNGs at max quality.
 * 
 * @author dereekb
 *
 */
public class ImageEditor {

	private ImagesService service;
	private OutputSettings editorOutputSettings;

	public ImageEditor() {
		this(ImagesServiceFactory.getImagesService());
	}

	public ImageEditor(ImagesService service) {
		this.service = service;
		this.editorOutputSettings = new OutputSettings(ImagesService.OutputEncoding.PNG);
		editorOutputSettings.setQuality(100);
	}

	public class ImageEditorInstance {

		private Image image;
		private List<Transform> transforms;
		private OutputSettings outputSettings;
		private boolean clearTransformsOnApply = true;
		private boolean setImageAfterTransform = true;

		private ImageEditorInstance(Image image) {
			this.image = image;
			this.clearTransforms();
			this.initializeOutputSettings();
		}

		private void initializeOutputSettings() {
			OutputSettings copy = new OutputSettings(editorOutputSettings.getOutputEncoding());
			copy.setQuality(editorOutputSettings.getQuality());
			this.setOutputSettings(copy);
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
		}

		public void flipImageVertically() {
			Transform flip = ImagesServiceFactory.makeVerticalFlip();
			this.transformImage(flip);
		}

		public void flipImageHorizontally() {
			Transform flip = ImagesServiceFactory.makeHorizontalFlip();
			this.transformImage(flip);
		}

		public void rotateImage(int degrees) {
			Transform rotate = ImagesServiceFactory.makeRotate(degrees);
			this.transformImage(rotate);
		}

		/**
		 * Resizes the image to fit in a box with the specified width/height, and does not allow stretching.
		 * 
		 * @param width
		 * @param height
		 */
		public void resizeImage(int width,
		                        int height) {
			this.resizeImage(width, height, false);
		}

		/**
		 * Resizes the image to fit in a box with the specified width/height.
		 * 
		 * @param width
		 * @param height
		 * @param allowStretch
		 */
		public void resizeImage(int width,
		                        int height,
		                        boolean allowStretch) {
			Transform resize = ImagesServiceFactory.makeResize(width, height, allowStretch);
			this.transformImage(resize);
		}

		/**
		 * Resizes the image to fit in the given box, and crops the most constraining dimension.
		 * 
		 * @param width
		 * @param height
		 * @param xCrop
		 * @param yCrop
		 */
		public void resizeImage(int width,
		                        int height,
		                        double xCrop,
		                        double yCrop) {
			Transform resize = ImagesServiceFactory.makeResize(width, height, xCrop, yCrop);
			this.transformImage(resize);
		}

		/**
		 * Crops the image, with the left and top set to 0.
		 * 
		 * @param right
		 *            Normalized (0-1) value of the right side.
		 * @param bottom
		 *            Normalized (0-1) value of the bottom side.
		 */
		public void cropImage(double right,
		                      double bottom) {
			this.cropImage(0, 0, right, bottom);
		}

		/**
		 * Crops the image.
		 * 
		 * @param left
		 *            Normalized (0-1) value of the left side.
		 * @param top
		 *            Normalized (0-1) value of the top side.
		 * @param right
		 *            Normalized (0-1) value of the right side.
		 * @param bottom
		 *            Normalized (0-1) value of the bottom side.
		 */
		public void cropImage(double left,
		                      double top,
		                      double right,
		                      double bottom) {
			Transform crop = ImagesServiceFactory.makeCrop(left, top, right, bottom);
			this.transformImage(crop);
		}

		public void transformImage(Transform transform) throws NullPointerException {
			if (transform == null) {
				throw new NullPointerException("Transform cannot be null.");
			}

			this.transforms.add(transform);
		}

		private CompositeTransform createTransformComposite() {
			CompositeTransform composite = ImagesServiceFactory.makeCompositeTransform(transforms);
			return composite;
		}

		/**
		 * Applies the transform to the given image.
		 * 
		 * The the created image becomes the new image for the editor if setImageAfterTransform is true.
		 * 
		 * @return
		 */
		public Image applyTransforms() {
			CompositeTransform composite = this.createTransformComposite();
			Image newImage = service.applyTransform(composite, image, this.outputSettings);

			if (this.setImageAfterTransform) {
				this.image = newImage;
			}

			if (this.clearTransformsOnApply) {
				this.clearTransforms();
			}

			return newImage;
		}

		public void clearTransforms() {
			this.transforms = new ArrayList<Transform>();
		}

		public List<Transform> getTransforms() {
			List<Transform> copy = new ArrayList<Transform>(this.transforms);
			return copy;
		}

		public void setTransforms(List<Transform> transforms) {
			this.transforms = transforms;
		}

		public boolean getClearTransformsOnApply() {
			return clearTransformsOnApply;
		}

		public void setClearTransformsOnApply(boolean clearTransformsOnApply) {
			this.clearTransformsOnApply = clearTransformsOnApply;
		}

		public boolean getSetImageAfterTransform() {
			return setImageAfterTransform;
		}

		public void setSetImageAfterTransform(boolean setImageAfterTransform) {
			this.setImageAfterTransform = setImageAfterTransform;
		}

		public boolean hasChanges() {
			boolean hasChanges = (this.transforms.isEmpty() == false);
			return hasChanges;
		}

		public OutputSettings getOutputSettings() {
			return outputSettings;
		}

		public void setOutputSettings(OutputSettings outputSettings) {
			this.outputSettings = outputSettings;
		}

	}

	public ImageEditorInstance forBytes(byte[] bytes) {
		Image image = ImagesServiceFactory.makeImage(bytes);
		ImageEditorInstance editor = new ImageEditorInstance(image);
		return editor;
	}

	public ImageEditorInstance forImage(Image image) {
		ImageEditorInstance editor = new ImageEditorInstance(image);
		return editor;
	}

	public ImagesService getService() {
		return service;
	}

	public void setService(ImagesService service) {
		this.service = service;
	}

	public OutputSettings getEditorOutputSettings() {
		return editorOutputSettings;
	}

	public void setEditorOutputSettings(OutputSettings editorOutputSettings) {
		this.editorOutputSettings = editorOutputSettings;
	}

}
