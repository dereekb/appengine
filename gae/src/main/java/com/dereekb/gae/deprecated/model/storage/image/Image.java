package com.thevisitcompany.gae.deprecated.model.storage.image;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.condition.IfNull;
import com.thevisitcompany.gae.deprecated.model.storage.StorageModel;
import com.thevisitcompany.gae.model.general.geo.Point;
import com.thevisitcompany.visit.models.info.annotation.InfoObjectTypeIdentifier;

/**
 * Represents an image in the datastore.
 *
 * @author dereekb
 */
@Cache
@Entity
@InfoObjectTypeIdentifier(6)
public class Image extends StorageModel<Image> {

	private static final long serialVersionUID = 1L;
	public static final String IMAGE_LINK_NAME = "image";

	@IgnoreSave(IfNull.class)
	private Point location;

	public Image() {}

	public Image(Long id) {
		super(id);
	}

	@Override
	public Key<Image> getKey() {
		return Key.create(Image.class, this.id);
	}

	public Point getLocation() {
		return this.location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public ImageType getImageType() {
		return ImageType.withBit(this.type);
	}

	public void setImageType(ImageType type) {
		if (type == null) {
			this.type = 0;
		} else {
			this.type = type.getBit();
		}
	}

	@Override
	public String getFilename() {
		String filename = this.id.toString();
		return filename;
	}

	@Override
	public boolean modelEquals(Image obj) {
		if (this == obj) {
	        return true;
        }
		if (!super.equals(obj)) {
	        return false;
        }
		Image other = obj;
		if (this.location == null) {
			if (other.location != null) {
	            return false;
            }
		} else if (!this.location.equals(other.location)) {
	        return false;
        }
		if (this.name == null) {
			if (other.name != null) {
	            return false;
            }
		} else if (!this.name.equals(other.name)) {
	        return false;
        }
		if (this.summary == null) {
			if (other.summary != null) {
	            return false;
            }
		} else if (!this.summary.equals(other.summary)) {
	        return false;
        }
		if (this.type == null) {
			if (other.type != null) {
	            return false;
            }
		} else if (!this.type.equals(other.type)) {
	        return false;
        }
		return true;
	}

	@Override
	public String toString() {
		return "Image [name=" + this.name + ", id=" + this.id + "]";
	}

}
