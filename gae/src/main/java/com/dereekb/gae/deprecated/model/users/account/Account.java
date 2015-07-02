package com.thevisitcompany.gae.deprecated.model.users.account;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.condition.IfDefault;
import com.googlecode.objectify.condition.IfEmpty;
import com.thevisitcompany.gae.deprecated.model.mod.relationships.RelationshipField;
import com.thevisitcompany.gae.deprecated.model.mod.relationships.modifiers.RelationshipKeySetModifier;
import com.thevisitcompany.gae.deprecated.model.users.UsersModel;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.visit.models.locations.place.Place;

/**
 * A Visit Account, which can be shared by multiple users.
 * 
 * This account is what holds all the information for businesses, payments, etc.
 * 
 * @author dereekb
 */
@Entity
@Cache
public final class Account extends UsersModel<Account> {

	private static final long serialVersionUID = 1L;

	@IgnoreSave(IfEmpty.class)
	private String name = "";

	@IgnoreSave(IfDefault.class)
	private AccountSettings settings = new AccountSettings();

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Login>> owners = new HashSet<Key<Login>>();

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Login>> members = new HashSet<Key<Login>>();

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Login>> viewers = new HashSet<Key<Login>>();

	@IgnoreSave(IfEmpty.class)
	private Set<Key<Place>> places = new HashSet<Key<Place>>();

	public Account() {}

	public Account(Long identifier) {
		super(identifier);
	}

	public Account(String name) {
		this.name = name;
	}

	@Override
	public Key<Account> getKey() {
		return Key.create(Account.class, this.id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AccountSettings getSettings() {
		return settings;
	}

	public void setSettings(AccountSettings settings) {
		this.settings = settings;
	}

	@RelationshipField(name = "owners", types = Login.class, modifierClass = RelationshipKeySetModifier.class)
	public Set<Key<Login>> getOwners() {
		return owners;
	}

	public void setOwners(Set<Key<Login>> owners) {
		this.owners = owners;
	}

	@RelationshipField(name = "members", types = Login.class, modifierClass = RelationshipKeySetModifier.class)
	public Set<Key<Login>> getMembers() {
		return members;
	}

	public void setMembers(Set<Key<Login>> members) {
		this.members = members;
	}

	@RelationshipField(name = "viewers", types = Login.class, modifierClass = RelationshipKeySetModifier.class)
	public Set<Key<Login>> getViewers() {
		return viewers;
	}

	public void setViewers(Set<Key<Login>> viewers) {
		this.viewers = viewers;
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
		return "Account [name='" + name + "', id=" + id + "]";
	}

	@Override
	public boolean modelEquals(Account object) {
		// TODO Auto-generated method stub
		return false;
	}

}
