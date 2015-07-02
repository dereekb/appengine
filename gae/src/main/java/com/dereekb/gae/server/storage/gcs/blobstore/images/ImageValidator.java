package com.dereekb.gae.server.storage.gcs.blobstore.images;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.Image.Format;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

/**
 * Validates Images
 * 
 * @author dereekb
 *
 */
public class ImageValidator {

	private ImagesService service;

	/**
	 * Ratio of width to height. 1.0 = 1:1, 4:3 = 4/3 = 1.333
	 */
	private BigDecimal aspectRatio;
	private Integer minWidth;
	private Integer minHeight;
	private Integer maxWidth = ImagesService.MAX_RESIZE_DIMENSIONS;
	private Integer maxHeight = ImagesService.MAX_RESIZE_DIMENSIONS;
	private Set<Format> acceptableFormats;

	public ImageValidator() {
		this.service = ImagesServiceFactory.getImagesService();
	}

	public ImageValidator(ImagesService service) {
		this.service = service;
	}

	public boolean validateImage(Image image) {
		boolean isValid = true;
		Integer width = image.getWidth();
		Integer height = image.getHeight();

		if (minWidth != null) {
			isValid = this.minWidth <= width;
		}

		if (isValid && minHeight != null) {
			isValid = this.minHeight <= height;
		}

		if (isValid && maxWidth != null) {
			isValid = this.maxWidth >= width;
		}

		if (isValid && maxHeight != null) {
			isValid = this.maxHeight >= height;
		}

		if (isValid && aspectRatio != null) {
			BigDecimal imageRatio = new BigDecimal(((1.0 * width) / height));
			imageRatio = imageRatio.setScale(4, BigDecimal.ROUND_DOWN);

			boolean validAspect = (aspectRatio.equals(imageRatio));
			isValid = validAspect;
		}

		if (isValid && acceptableFormats != null && acceptableFormats.isEmpty() == false) {
			Format format = image.getFormat();
			isValid = this.acceptableFormats.contains(format);
		}

		return isValid;
	}

	private boolean isAcceptableImage(Image image) {
		boolean hasFormat = (image.getFormat() != null);
		boolean hasValidDimensions = (image.getHeight() > 0) && (image.getWidth() > 0);
		boolean isAcceptable = hasFormat && hasValidDimensions;

		return isAcceptable;
	}

	public boolean validateImage(byte[] bytes) {
		boolean isValid = false;

		Image image = ImagesServiceFactory.makeImage(bytes);

		try {
			if (image != null) {
				isValid = (this.isAcceptableImage(image) && this.validateImage(image));
			}
		} catch (Exception e) {

		}

		return isValid;
	}

	public ImagesService getService() {
		return service;
	}

	public void setService(ImagesService service) {
		this.service = service;
	}

	public Integer getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(Integer minWidth) throws IllegalArgumentException {
		if (minWidth != null && minWidth < 0) {
			throw new IllegalArgumentException("Cannot set an impossible/useless dimension for ImageValidator.");
		}

		this.minWidth = minWidth;
	}

	public Integer getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Integer minHeight) throws IllegalArgumentException {
		if (minHeight != null && minHeight < 0) {
			throw new IllegalArgumentException("Cannot set an impossible/useless dimension for ImageValidator.");
		}

		this.minHeight = minHeight;
	}

	public Set<Format> getAcceptableFormats() {
		Set<Format> copy = null;

		if (this.acceptableFormats != null) {
			copy = new HashSet<Format>(this.acceptableFormats);
		}

		return copy;
	}

	public void setAcceptableFormats(Set<Format> acceptableFormats) throws IllegalArgumentException {
		if (acceptableFormats != null && acceptableFormats.isEmpty()) {
			throw new IllegalArgumentException("FormatsSet cannot be empty for ImageValidator");
		}

		this.acceptableFormats = new HashSet<Format>(acceptableFormats);
	}

	public Integer getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(Integer maxWidth) {
		this.maxWidth = maxWidth;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Double getAspectRatio() {
		return aspectRatio.doubleValue();
	}

	public void setAspectRatio(Double aspectRatio) {
		this.aspectRatio = new BigDecimal(aspectRatio);
		this.aspectRatio = this.aspectRatio.setScale(4, BigDecimal.ROUND_DOWN);
	}

}
