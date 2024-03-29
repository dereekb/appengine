package com.dereekb.gae.server.storage.services.images;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.storage.exception.InvalidFileDataException;
import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.OutputSettings;
import com.google.appengine.api.images.Transform;

/**
 * Used for manipulating {@link byte[]} data for an image.
 *
 * @author dereekb
 *
 */
public class ImageBytesEditor {

	private ImagesService service;
	private OutputSettings outputSettings;

	public ImageBytesEditor() {
		this.service = ImagesServiceFactory.getImagesService();
		this.outputSettings = defaultOutputSettings();
	}

	public ImageBytesEditor(OutputSettings outputSettings) {
		this.service = ImagesServiceFactory.getImagesService();
		this.outputSettings = outputSettings;
	}

	public ImageBytesEditor(ImagesService service, OutputSettings outputSettings) {
		this.service = service;
		this.outputSettings = outputSettings;
	}

	/**
	 * @return Default {@link OutputSettings} that transforms objects to PNGs
	 *         with 100% quality.
	 */
	public static OutputSettings defaultOutputSettings() {
		OutputSettings settings = new OutputSettings(ImagesService.OutputEncoding.PNG);
		settings.setQuality(100);
		return settings;
	}

	public ImagesService getService() {
    	return this.service;
    }

    public void setService(ImagesService service) {
    	this.service = service;
    }

    public OutputSettings getOutputSettings() {
    	return this.outputSettings;
    }

    public void setOutputSettings(OutputSettings outputSettings) {
    	this.outputSettings = outputSettings;
    }


	public Instance newInstance(byte[] bytes) throws InvalidFileDataException {
		Image image = this.buildImageFromBytes(bytes);
		Instance instance = new Instance(image);
		return instance;
	}

	public Instance newInstance(Image image) {
		Instance instance = new Instance(image);
		return instance;
	}

	public Image buildImageFromBytes(byte[] bytes) throws InvalidFileDataException {
		Image image = ImagesServiceFactory.makeImage(bytes);

		if (image == null) {
			throw new InvalidFileDataException("Failed to create image from bytes.");
		}

		return image;
	}

	/**
	 * Instance generated by {@link ImageBytesEditor}.
	 *
	 * @author dereekb
	 */
	public class Instance {

		private Image image;

		/**
		 * List of unapplied {@link Transform} values.
		 */
		private List<Transform> transforms;

		private Instance(Image image) {
			this.image = image;
			this.transforms = new ArrayList<Transform>();
		}

		public Image getImage() {
			return this.image;
		}

		public void setImage(Image image) {
			this.image = image;
		}

		public List<Transform> getTransforms() {
			return this.transforms;
		}

		public void clearTransforms() {
			this.transforms = new ArrayList<Transform>();
		}

		// MARK: Transformations
		public void flipVertically() {
			Transform flip = ImagesServiceFactory.makeVerticalFlip();
			this.addTransform(flip);
		}

		public void flipHorizontally() {
			Transform flip = ImagesServiceFactory.makeHorizontalFlip();
			this.addTransform(flip);
		}

		public void rotateImage(int degrees) {
			Transform rotate = ImagesServiceFactory.makeRotate(degrees);
			this.addTransform(rotate);
		}

		/**
		 * Resizes the image to fit in a box with the specified width/height,
		 * and does not allow stretching.
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
			this.addTransform(resize);
		}

		/**
		 * Resizes the image to fit in the given box, and crops the most
		 * constraining dimension.
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
			this.addTransform(resize);
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
			this.addTransform(crop);
		}

		public void addTransform(Transform transform) throws NullPointerException {
			if (transform == null) {
				throw new IllegalArgumentException("Cannot add null Transform.");
			}

			this.transforms.add(transform);
		}

		/**
		 * Applies the transforms and returns the created {@link Image} .
		 *
		 * @return {@link Image} created by the transformations.
		 */
		public Image createEditedImage() {
			CompositeTransform composite = ImagesServiceFactory.makeCompositeTransform(this.transforms);
			Image newImage = ImageBytesEditor.this.service.applyTransform(composite, this.image,
			        ImageBytesEditor.this.outputSettings);
			return newImage;
		}

	}

	@Override
	public String toString() {
		return "ImageBytesEditor [indexService=" + this.service + ", outputSettings=" + this.outputSettings + "]";
	}

}
