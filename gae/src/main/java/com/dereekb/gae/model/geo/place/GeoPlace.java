package com.dereekb.gae.model.geo.place;

import java.util.Date;

import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedDatabaseModel;
import com.dereekb.gae.model.extension.objectify.annotation.IfNew;
import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.model.general.geo.Region;
import com.dereekb.gae.model.general.geo.annotation.IfEmptyRegion;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.condition.IfNotNull;
import com.googlecode.objectify.condition.IfNull;

/**
 * Represents a point in the world.
 *
 * @author dereekb
 */
@Cache
@Entity
public final class GeoPlace extends DescribedDatabaseModel
        implements ObjectifyModel<GeoPlace> {

	private static final long serialVersionUID = 1L;

	/**
	 * Database identifier.
	 */
	@Id
	private Long identifier;

	/**
	 * Date the data was uploaded/created.
	 */
	@Index
	private Date date = new Date();

	// Geo Point Info
	@IgnoreSave({ IfNull.class, IfNew.class })
	private Point point;

	@IgnoreSave({ IfNull.class, IfEmptyRegion.class })
	private Region region;

	// Parent
	/**
	 * Parent {@link GeoPlace} that is related to/contains this place.
	 */
	@Index({ IfNotNull.class })
	@IgnoreSave({ IfNull.class })
	private Key<GeoPlace> parent;

	// TODO: Add address and other related info?

	public GeoPlace() {}

	public GeoPlace(Long identifier) {
		this.identifier = identifier;
	}

	public Long getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public boolean isPoint() {
		return (this.point != null);
	}

	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public boolean isRegion() {
		return (this.point == null && this.region != null);
	}

	public Key<GeoPlace> getParent() {
		return this.parent;
	}

	@Deprecated
	public Long getParentLongKey() {
		Long key = null;

		if (this.parent != null) {
			key = this.parent.getId();
		}

		return key;
	}

	public void setParent(Key<GeoPlace> parent) {
		this.parent = parent;
	}

	// Unique Model
	@Override
	public ModelKey getModelKey() {
		return ModelKey.safe(this.identifier);
	}

	public void setModelKey(ModelKey key) {
		this.identifier = ModelKey.readIdentifier(key);
	}

	// Database Model
	@Override
	protected Object getDatabaseIdentifier() {
		return this.identifier;
	}

	// Objectify Model
	@Override
	public Key<GeoPlace> getObjectifyKey() {
		return Key.create(GeoPlace.class, this.identifier);
	}

	@Override
	public String toString() {
		return "GeoPlace [identifier=" + this.identifier + ", date=" + this.date + ", point=" + this.point
		        + ", region=" + this.region + ", parent=" + this.parent + ", descriptorType=" + this.descriptorType
		        + ", descriptorId=" + this.descriptorId + ", searchIdentifier=" + this.searchIdentifier + "]";
	}

}
