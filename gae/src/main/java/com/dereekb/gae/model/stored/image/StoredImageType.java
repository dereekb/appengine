package com.dereekb.gae.model.stored.image;

/**
 * Describes the type of {@link StoredImage}.
 *
 * @author dereekb
 */
public enum StoredImageType {

	/**
	 * An normal image.
	 * <p>
	 * Aspect Ratio: Unspecified <br>
	 * Min Size: 256x256 <br>
	 * Max Size: 2048x1536
	 */
	IMAGE(0, "image"),

	/**
	 * Icon image.
	 * <p>
	 *
	 * Aspect Ratio: 1:1 <br>
	 * Min: 128x128 <br>
	 * Max: 256x256
	 */
	ICON(1, "icon"),

	/**
	 * Image restricted to the 16:9 ratio and is atleast 720p.
	 * <p>
	 *
	 * Aspect Ratio: 16:9 <br>
	 * Min: 1280×720 <br>
	 * Max: 1920×1080
	 */
	HD_IMAGE(2, "hd");

	private final int type;
	private final String abbreviation;

	private StoredImageType(int type,
	                  String abbreviation) {
		this.type = type;
		this.abbreviation = abbreviation;
	}

	public int getType() {
		return this.type;
	}

	public String getTypeName() {
		return this.abbreviation;
	}

	public static StoredImageType typeForId(Integer type) {
		StoredImageType imageType = StoredImageType.IMAGE;

		switch (type) {
			case 0:
				imageType = StoredImageType.IMAGE;
				break;
			case 1:
				imageType = StoredImageType.ICON;
				break;
			case 2:
				imageType = StoredImageType.HD_IMAGE;
				break;
			default:
				imageType = StoredImageType.IMAGE;
				break;
		}

		return imageType;
	}

}
