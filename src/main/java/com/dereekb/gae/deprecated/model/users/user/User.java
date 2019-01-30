package com.thevisitcompany.gae.deprecated.model.users.user;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.condition.IfEmpty;
import com.thevisitcompany.gae.deprecated.model.mod.relationships.RelationshipField;
import com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers.RelationshipKeySetModifier;
import com.thevisitcompany.gae.deprecated.model.users.UsersModel;
import com.thevisitcompany.visit.models.locations.destination.Destination;
import com.thevisitcompany.visit.models.locations.place.Place;
import com.thevisitcompany.visit.models.locations.series.Series;

/**
 * A user's account. This does not include login information.
 * 
 * @author dereekb
 * 
 */
@Cache
@Entity
public final class User extends UsersModel<User> {

	private static final long serialVersionUID = 1L;

	@IgnoreSave(IfEmpty.class)
	private String name = "";

	@IgnoreSave(IfEmpty.class)
	private String company = "";

	@IgnoreSave(IfEmpty.class)
	private String phonenumber = "";

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Destination>> destinations = new HashSet<Key<Destination>>();

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Series>> series = new HashSet<Key<Series>>();

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Place>> places = new HashSet<Key<Place>>();

	public User() {}

	@Override
	public Key<User> getKey() {
		return Key.create(User.class, this.id);
	}

	public User(Long identifier) {
		super(identifier);
	}

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@RelationshipField(types = Destination.class, modifierClass = RelationshipKeySetModifier.class)
	public Set<Key<Destination>> getDestinations() {
		return destinations;
	}

	public void setDestinations(Set<Key<Destination>> destination) {
		this.destinations = destination;
	}

	@RelationshipField(types = Series.class, modifierClass = RelationshipKeySetModifier.class)
	public Set<Key<Series>> getSeries() {
		return series;
	}

	public void setSeries(Set<Key<Series>> series) {
		this.series = series;
	}

	@RelationshipField(types = Place.class, modifierClass = RelationshipKeySetModifier.class)
	public Set<Key<Place>> getPlaces() {
		return places;
	}

	public void setPlaces(Set<Key<Place>> places) {
		this.places = places;
	}

	@Override
	public String toString() {
		return "User [name='" + name + "', id=" + id + "]";
	}

	@Override
    public boolean modelEquals(User object) {
	    // TODO Auto-generated method stub
	    return false;
    }
}
