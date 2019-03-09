package com.dereekb.gae.server.storage.upload.data.image;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.utilities.data.BytesValidator;
import com.dereekb.gae.utilities.validation.ContentValidationException;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

/**
 * {@link BytesValidator} implementation for validating an image.
 *
 * @author dereekb
 *
 */
public class ImageBytesValidator
        implements BytesValidator {

	private static final int MAX_ALLOWED_WIDTH = ImagesService.MAX_RESIZE_DIMENSIONS;
	private static final int MAX_ALLOWED_HEIGHT = ImagesService.MAX_RESIZE_DIMENSIONS;

	/**
	 * Ratio of width to height. 1.0 = 1:1, 4:3 = 4/3 = 1.333
	 */
	private BigDecimal aspectRatio;

	private int minWidth = 0;
	private int minHeight = 0;

	private int maxWidth = MAX_ALLOWED_WIDTH;
	private int maxHeight = MAX_ALLOWED_HEIGHT;

	private Set<Format> acceptableFormats = new HashSet<Format>();

	public ImageBytesValidator() {}

	public ImageBytesValidator(Double aspectRatio, int minWidth, int minHeight) {
		this.setAspectRatio(aspectRatio);
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
	}

	public ImageBytesValidator(Double aspectRatio,
	        int minWidth,
	        int minHeight,
	        int maxWidth,
 int maxHeight) {
		this(aspectRatio, minWidth, minHeight, maxWidth, maxHeight, null);
	}

	public ImageBytesValidator(Double aspectRatio,
	        int minWidth,
	        int minHeight,
	        int maxWidth,
	        int maxHeight,
	        Set<Format> acceptableFormats) {
		this.setAspectRatio(aspectRatio);
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
		this.setMaxWidth(maxWidth);
		this.setMaxHeight(maxHeight);
		this.setAcceptableFormats(acceptableFormats);
	}

	public Double getAspectRatio() {
		return this.aspectRatio.doubleValue();
	}

	public void setAspectRatio(Double aspectRatio) {
		this.aspectRatio = new BigDecimal(aspectRatio);
		this.aspectRatio = this.aspectRatio.setScale(4, BigDecimal.ROUND_DOWN);
	}

	public int getMinWidth() {
		return this.minWidth;
	}

	public void setMinWidth(int minWidth) throws IllegalArgumentException {
		if (minWidth < 0) {
			throw new IllegalArgumentException("Min width must be atleast 1px.");
		}

		this.minWidth = minWidth;
	}

	public int getMinHeight() {
		return this.minHeight;
	}

	public void setMinHeight(int minHeight) throws IllegalArgumentException {
		if (minHeight < 0) {
			throw new IllegalArgumentException("Min height must be atleast 1px.");
		}

		this.minHeight = minHeight;
	}

	public int getMaxWidth() {
		return this.maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		if (maxWidth > MAX_ALLOWED_WIDTH) {
			throw new IllegalArgumentException("Max allowed width is " + MAX_ALLOWED_WIDTH);
		}

		this.maxWidth = maxWidth;
	}

	public int getMaxHeight() {
		return this.maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		if (maxHeight > MAX_ALLOWED_HEIGHT) {
			throw new IllegalArgumentException("Max allowed height is " + MAX_ALLOWED_HEIGHT);
		}

		this.maxHeight = maxHeight;
	}

	public Set<Format> getAcceptableFormats() {
		Set<Format> copy = null;

		if (this.acceptableFormats != null) {
			copy = new HashSet<Format>(this.acceptableFormats);
		}

		return copy;
	}

	public void setAcceptableFormats(Set<Format> acceptableFormats) {
		if (acceptableFormats == null) {
			acceptableFormats = new HashSet<Format>();
		}

		this.acceptableFormats = new HashSet<Format>(acceptableFormats);
	}

	// MARK: BytesValidator
	@Override
	public void validateContent(byte[] bytes) throws ContentValidationException {
		Instance instance = new Instance(bytes);
		instance.validate();
	}

	public boolean safeValidateContent(byte[] bytes) {
		boolean valid = true;

		try {
			this.validateContent(bytes);
		} catch (ContentValidationException e) {
			valid = false;
		}

		return valid;
	}

	private class Instance {

		private final Image image;

		public Instance(byte[] bytes) {
			this.image = ImagesServiceFactory.makeImage(bytes);

			if (this.image == null) {
				throw new ContentValidationException("Image", null, "Could not create an image.");
			}
		}

		public void validate() throws ContentValidationException {
			this.validateFormat();
			this.validateImageParameters();
		}

		public void validateFormat() throws ContentValidationException {
			if (this.image.getFormat() == null) {
				throw new ContentValidationException("Format", null, "Unknown image format.");
			}

			if (ImageBytesValidator.this.acceptableFormats.isEmpty() == false) {
				Format format = this.image.getFormat();

				if (ImageBytesValidator.this.acceptableFormats.contains(format) == false) {
					throw new ContentValidationException("Format", format, "Unacceptable format type.");
				}
			}
		}

		public void validateImageParameters() throws ContentValidationException {

			// MARK: Dimensions
			int width = this.image.getWidth();
			int height = this.image.getHeight();

			if (width < ImageBytesValidator.this.minWidth) {
				throw new ContentValidationException("Width", width, "Image's width is less than the minimum width.");
			}

			if (height < ImageBytesValidator.this.minHeight) {
				throw new ContentValidationException("Height", width, "Image's height is less than the minimum height.");
			}

			if (width > ImageBytesValidator.this.maxWidth) {
				throw new ContentValidationException("Width", width, "Image's width is more than the max width.");
			}

			if (height > ImageBytesValidator.this.maxHeight) {
				throw new ContentValidationException("Height", width, "Image's height is more than the max height.");
			}

			// MARK: Ratio
			if (ImageBytesValidator.this.aspectRatio != null) {
				BigDecimal imageRatio = new BigDecimal(new Double(width) / new Double(height));
				imageRatio = imageRatio.setScale(4, BigDecimal.ROUND_DOWN);

				if (ImageBytesValidator.this.aspectRatio.equals(imageRatio) == false) {
					throw new ContentValidationException("Ratio", imageRatio,
					        "Image's ratio was not equal to expected ratio.");
				}

			}
		}
	}
}
