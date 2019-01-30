package com.thevisitcompany.gae.deprecated.model.users.user.functions;

import java.util.Collection;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.users.login.Login;
import com.thevisitcompany.gae.deprecated.model.users.user.User;
import com.thevisitcompany.gae.model.extension.links.functions.LinksAction;
import com.thevisitcompany.gae.model.extension.links.functions.LinksMethod;
import com.thevisitcompany.gae.model.extension.links.functions.LinksPair;
import com.thevisitcompany.gae.model.extension.links.functions.filters.CanLinkFilterDelegate;
import com.thevisitcompany.visit.models.locations.destination.Destination;
import com.thevisitcompany.visit.models.locations.place.Place;
import com.thevisitcompany.visit.models.locations.series.Series;

public class UserLinksFunctionDelegate
        implements CanLinkFilterDelegate<Login> {

	@LinksMethod(Place.PLACE_LINK_NAME)
	public void linkPlace(LinksPair<User, Long> pair) {
		User object = pair.getTarget();
		Set<Key<Place>> placeSet = object.getPlaces();

		Collection<Long> placeIds = pair.getLinks();
		Set<Key<Place>> placeKeys = Place.createKeySet(Place.class, placeIds);
		placeSet.addAll(placeKeys);
	}

	@LinksMethod(value = Place.PLACE_LINK_NAME, action = LinksAction.UNLINK)
	public void unlinkPlace(LinksPair<User, Long> pair) {
		User object = pair.getTarget();
		Set<Key<Place>> placeSet = object.getPlaces();

		Collection<Long> placeIds = pair.getLinks();
		Set<Key<Place>> placeKeys = Place.createKeySet(Place.class, placeIds);
		placeSet.removeAll(placeKeys);
	}

	@LinksMethod(Series.SERIES_LINK_NAME)
	public void linkUser(LinksPair<User, Long> pair) {
		User object = pair.getTarget();
		Set<Key<Series>> seriesSet = object.getSeries();

		Collection<Long> seriesIds = pair.getLinks();
		Set<Key<Series>> seriesKeys = Series.createKeySet(Series.class, seriesIds);
		seriesSet.addAll(seriesKeys);
	}

	@LinksMethod(value = Series.SERIES_LINK_NAME, action = LinksAction.UNLINK)
	public void unlinkUser(LinksPair<User, Long> pair) {
		User object = pair.getTarget();
		Set<Key<Series>> seriesSet = object.getSeries();

		Collection<Long> seriesIds = pair.getLinks();
		Set<Key<Series>> seriesKeys = Series.createKeySet(Series.class, seriesIds);
		seriesSet.removeAll(seriesKeys);
	}

	@LinksMethod(Destination.DESTINATION_LINK_NAME)
	public void linkDestination(LinksPair<User, Long> pair) {
		User object = pair.getTarget();
		Set<Key<Destination>> destinationSet = object.getDestinations();

		Collection<Long> destinationIds = pair.getLinks();
		Set<Key<Destination>> destinationKeys = Destination.createKeySet(Destination.class, destinationIds);
		destinationSet.addAll(destinationKeys);
	}

	@LinksMethod(value = Destination.DESTINATION_LINK_NAME, action = LinksAction.UNLINK)
	public void unlinkDestination(LinksPair<User, Long> pair) {
		User object = pair.getTarget();
		Set<Key<Destination>> destinationSet = object.getDestinations();

		Collection<Long> destinationIds = pair.getLinks();
		Set<Key<Destination>> destinationKeys = Destination.createKeySet(Destination.class, destinationIds);
		destinationSet.removeAll(destinationKeys);
	}

	@Override
	public boolean canLink(Login object,
	                       String link,
	                       LinksAction action) {
		// TODO Auto-generated method stub
		return false;
	}

}
