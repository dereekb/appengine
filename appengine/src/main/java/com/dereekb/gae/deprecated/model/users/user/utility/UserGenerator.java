package com.thevisitcompany.gae.deprecated.model.users.user.utility;

import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.gae.model.objectify.generator.ObjectifyModelGenerator;
import com.thevisitcompany.visit.models.locations.destination.Destination;
import com.thevisitcompany.visit.models.locations.place.Place;
import com.thevisitcompany.visit.models.locations.series.Series;

public class UserGenerator extends ObjectifyModelGenerator<User> {

	@Override
	public User generateModel(Long identifier) {
		User user = new User(identifier);

		user.setName("User Name");
		user.setPhonenumber("123-456-7890");
		user.setCompany("Company");

		Set<Key<Destination>> destinations = new HashSet<Key<Destination>>();
		destinations.add(Key.create(Destination.class, 1));
		user.setDestinations(destinations);

		Set<Key<Series>> series = new HashSet<Key<Series>>();
		series.add(Key.create(Series.class, 11));
		user.setSeries(series);

		Set<Key<Place>> places = new HashSet<Key<Place>>();
		places.add(Key.create(Place.class, 111));
		places.add(Key.create(Place.class, 112));
		places.add(Key.create(Place.class, 113));
		user.setPlaces(places);

		return user;
	}

}
