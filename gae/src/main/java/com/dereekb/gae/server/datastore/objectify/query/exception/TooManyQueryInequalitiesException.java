package com.dereekb.gae.server.datastore.objectify.query.exception;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.utilities.query.exception.IllegalQueryArgumentException;

/**
 * Thrown by a query during configuration if too many inequalities have been
 * passed.
 * 
 * @author dereekb
 *
 */
public class TooManyQueryInequalitiesException extends IllegalQueryArgumentException {

	private ObjectifyQueryFilter inequalityFilter;
	private ObjectifyQueryFilter secondFilter;

	private static final long serialVersionUID = 1L;

	public TooManyQueryInequalitiesException(ObjectifyQueryFilter inequalityFilter, ObjectifyQueryFilter secondFilter) {
		super("Too many inequalities in query." + inequalityFilter.getField() + " and " + secondFilter.getField());
		this.inequalityFilter = inequalityFilter;
		this.secondFilter = secondFilter;
	}

	public ObjectifyQueryFilter getInequalityFilter() {
		return this.inequalityFilter;
	}

	public ObjectifyQueryFilter getSecondFilter() {
		return this.secondFilter;
	}

}
