package com.dereekb.gae.model.stored.blob;

/**
 * Describes the MIME-Type of the saved blob.
 *
 * @author dereekb
 */
public enum StoredBlobType {

	/**
	 * .blob
	 */
	BLOB(0, "blob", "application/octet-stream", "binary"),

	/**
	 * .jpg
	 */
	JPG(1, "jpg", "image/jpeg", "image"),

	/**
	 * .png
	 */
	PNG(2, "png", "image/png", "image"),

	/**
	 * .json
	 */
	JSON(3, "json", "application/json", "text"),

	/**
	 * .txt
	 */
	TXT(4, "txt", "text/plain", "text");

	public static final Integer DEFAULT_TYPE_ID = 0;

	public final Integer id;
	public final String ending;
	public final String mimeType;
	public final String fileType;

	private StoredBlobType(Integer id, String ending, String mimeType, String fileType) {
		this.id = id;
		this.ending = ending;
		this.mimeType = mimeType;
		this.fileType = fileType;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getType() {
		return this.id;
	}

	public String getEnding() {
		return this.ending;
	}

	public String getMimeType() {
		return this.mimeType;
	}

	public String getFileType() {
		return this.fileType;
	}

	public static StoredBlobType valueOf(Integer typeId) {
		StoredBlobType type = BLOB;

		switch (typeId) {
			case 0:
				type = BLOB;
				break;
			case 1:
				type = JPG;
				break;
			case 2:
				type = PNG;
				break;
			case 3:
				type = JSON;
				break;
		}

		return type;
	}

}
