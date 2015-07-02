package com.thevisitcompany.gae.deprecated.model.storage.image;

/**
 * Helper class that defines the types of images.
 * 
 * @author dereekb
 */
public enum ImageType {

	/**
	 * An normal image.
	 * 
	 * Min Size: 256x256
	 * Max Size: 2048x1536
	 */
	IMAGE(0, "image"),

	/**
	 * Icon image.
	 * 
	 * Aspect Ratio: 1:1
	 * Min: 128x128
	 * Max: 256x256
	 */
	ICON(1, "icon"),

	/**
	 * Location Model image. Is restricted more than a normal image.
	 * 
	 * Aspect Ratio: 16:9
	 * Min: 1280×720
	 * Max: 1920×1080
	 */
	LOCATION(2, "location");

	private final int bit;

	private final String abbreviation;

	private ImageType(int bit, String abbreviation) {
		this.bit = bit;
		this.abbreviation = abbreviation;
	}

	public int getBit() {
		return this.bit;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public static ImageType withBit(Integer type) {
		ImageType imageType = ImageType.IMAGE;

		switch (type) {
			case 0:
				imageType = ImageType.IMAGE;
				break;
			case 1:
				imageType = ImageType.ICON;
				break;
			case 2:
				imageType = ImageType.LOCATION;
				break;
		}

		return imageType;
	}
}
