package com.thevisitcompany.gae.deprecated.model.users.user.dto;

import java.util.List;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.mod.data.SerializerPair;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionDelegate;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConversionFunction;
import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.visit.models.locations.destination.Destination;
import com.thevisitcompany.visit.models.locations.place.Place;
import com.thevisitcompany.visit.models.locations.series.Series;

public class UserDataSerializer
        implements ModelConversionDelegate<User, UserData> {

	@Override
	public User convertDataToObject(UserData data) {
		User object = new User(data.getId());

		object.setName(data.getName());
		object.setCompany(data.getCompany());
		object.setPhonenumber(data.getPhonenumber());

		List<Long> destinationKeys = data.getDestinations();
		Set<Key<Destination>> destinations = Series.createKeySet(Destination.class, destinationKeys);
		object.setDestinations(destinations);

		List<Long> seriesKeys = data.getSeries();
		Set<Key<Series>> series = Series.createKeySet(Series.class, seriesKeys);
		object.setSeries(series);

		List<Long> placesKeys = data.getPlaces();
		Set<Key<Place>> places = Place.createKeySet(Place.class, placesKeys);
		object.setPlaces(places);

		return object;
	}

	@ModelConversionFunction(isDefault = true, value = ModelConversionDelegate.DEFAULT_DATA_NAME)
	public boolean convertToArchive(SerializerPair<User, UserData> pair) {

		User object = (User) pair.getSource();
		UserData archive = new UserData();

		archive.setId(object.getId());
		archive.setName(object.getName());
		archive.setCompany(object.getCompany());
		archive.setPhonenumber(object.getPhonenumber());

		pair.setResult(archive);
		return true;
	}

	@ModelConversionFunction(value = "full")
	public boolean convertToBackupArchive(SerializerPair<User, UserData> pair) {

		User object = (User) pair.getSource();
		UserData archive = new UserData();

		archive.setId(object.getId());
		archive.setName(object.getName());
		archive.setCompany(object.getCompany());
		archive.setPhonenumber(object.getPhonenumber());

		Set<Key<Destination>> destinations = object.getDestinations();
		List<Long> destinationsKeys = Destination.readKeyIdentifiers(destinations);
		archive.setDestinations(destinationsKeys);

		Set<Key<Series>> series = object.getSeries();
		List<Long> seriesKeys = Series.readKeyIdentifiers(series);
		archive.setSeries(seriesKeys);

		Set<Key<Place>> places = object.getPlaces();
		List<Long> placesKeys = Place.readKeyIdentifiers(places);
		archive.setPlaces(placesKeys);

		pair.setResult(archive);
		return true;
	}

}
