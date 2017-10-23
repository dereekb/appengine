package com.dereekb.gae.server.datastore.objectify.query.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyEntityQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestOptions;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifySimpleQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.exception.InvalidQuerySortingException;
import com.dereekb.gae.server.datastore.objectify.query.exception.TooManyQueryInequalitiesException;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrderingChain;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingChainImpl;
import com.dereekb.gae.utilities.collections.IteratorUtility;
import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link ObjectifyQueryRequestBuilder} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryRequestBuilderImpl<T extends ObjectifyModel<T>>
        implements ObjectifyQueryRequestBuilder<T> {

	private final ObjectifyEntityQueryService<T> queryService;

	private ObjectifyQueryFilter inequalityFilter;

	private List<ObjectifyQueryFilter> queryFilters;
	private List<ObjectifySimpleQueryFilter<T>> simpleQueryFilters;

	private ObjectifyQueryRequestOptions options;
	private Chain<ObjectifyQueryOrdering> resultsOrdering;

	public ObjectifyQueryRequestBuilderImpl(ObjectifyEntityQueryService<T> queryService)
	        throws IllegalArgumentException {
		if (queryService == null) {
			throw new IllegalArgumentException("Service cannot be null.");
		}

		this.queryService = queryService;
		this.setQueryFilters(null);
		this.setSimpleQueryFilters(null);
	}

	public ObjectifyQueryFilter getInequalityFilter() {
		return this.inequalityFilter;
	}

	@Override
	public List<ObjectifyQueryFilter> getQueryFilters() {
		return this.queryFilters;
	}

	@Override
	public void setQueryFilters(Iterable<ObjectifyQueryFilter> filters) throws TooManyQueryInequalitiesException {
		List<ObjectifyQueryFilter> queryFilters = IteratorUtility.iterableToList(filters);
		this.setQueryFilters(queryFilters);
	}

	public void setQueryFilters(List<ObjectifyQueryFilter> queryFilters) throws TooManyQueryInequalitiesException {
		this.inequalityFilter = null;
		this.queryFilters = new ArrayList<ObjectifyQueryFilter>();

		if (queryFilters != null) {
			for (ObjectifyQueryFilter filter : queryFilters) {
				this.addQueryFilter(filter);
			}
		}
	}

	@Override
	public void addQueryFilter(ObjectifyQueryFilter filter) throws TooManyQueryInequalitiesException {
		if (filter != null) {
			this.assertNotInequalityFilter(filter);
			
			if (filter.isInequality()) {
				this.inequalityFilter = filter;
			}
			
			this.queryFilters.add(filter);
		}
	}

	private void assertNotInequalityFilter(ObjectifyQueryFilter filter) throws TooManyQueryInequalitiesException {
		if (filter.isInequality() && this.inequalityFilter != null) {
			String field = this.inequalityFilter.getField();

			if (!field.equals(filter.getField())) {
				throw new TooManyQueryInequalitiesException(this.inequalityFilter, filter);
			}
		}
	}

	@Override
	public List<ObjectifySimpleQueryFilter<T>> getSimpleQueryFilters() {
		return this.simpleQueryFilters;
	}

	@Override
	public void addSimpleQueryFilter(ObjectifySimpleQueryFilter<T> filter) {
		if (filter != null) {
			this.simpleQueryFilters.add(filter);
		}
	}

	@Override
	public void setSimpleQueryFilters(Iterable<ObjectifySimpleQueryFilter<T>> filters) {
		List<ObjectifySimpleQueryFilter<T>> queryFilters = IteratorUtility.iterableToList(filters);
		this.setSimpleQueryFilters(queryFilters);
	}

	public void setSimpleQueryFilters(List<ObjectifySimpleQueryFilter<T>> simpleQueryFilters) {
		this.simpleQueryFilters = new ArrayList<ObjectifySimpleQueryFilter<T>>();

		if (simpleQueryFilters != null) {
			this.simpleQueryFilters.addAll(simpleQueryFilters);
		}
	}

	@Override
	public ObjectifyQueryRequestOptions getOptions() {
		return this.options;
	}

	@Override
	public void setOptions(ObjectifyQueryRequestOptions options) {
		this.options = options;
	}

	@Override
	public Chain<ObjectifyQueryOrdering> getResultsOrdering() {
		return this.resultsOrdering;
	}

	public void setResultsOrdering(ObjectifyQueryOrdering ordering) {
		if (ordering == null) {
			this.resultsOrdering = null;
		} else {
			ObjectifyQueryOrderingChain chain = new ObjectifyQueryOrderingChainImpl(ordering);
			this.setResultsOrdering(chain);
		}
	}

	@Override
	public void addResultsOrdering(ObjectifyQueryOrdering ordering) {
		if (this.resultsOrdering != null) {
			this.resultsOrdering.chain(ordering);
		} else {
			this.setResultsOrdering(ordering);
		}
	}

	@Override
	public void setResultsOrdering(Chain<ObjectifyQueryOrdering> orderingChain) {
		this.resultsOrdering = orderingChain;
	}

	private void tryReorderChain() {
		this.reorderFilters();
		if (this.resultsOrdering != null && this.queryFilters.isEmpty() == false) {
			List<ObjectifyQueryOrdering> order = IteratorUtility.iterableToList(this.getResultsOrdering());

			if (order.size() > 1) {
				ObjectifyQueryFilter firstFilter = this.getQueryFilters().get(0);
				ObjectifyQueryOrdering first = order.get(0);

				if (first.getField().equals(firstFilter.getField())) {
					return;	// Do nothing. Already sorted.
				} else {
					this.reorderChain(order);
				}
			}
		}
	}

	/**
	 * Re-orders the filters so {{@link #inequalityFilter} comes first, if
	 * available.
	 */
	private void reorderFilters() {
		if (this.inequalityFilter != null) {
			ListUtility.sendToFront(this.inequalityFilter, this.queryFilters);
		}
	}

	private void reorderChain(List<ObjectifyQueryOrdering> order) {
		String inequalityField = this.getQueryFilters().get(0).getField();

		int inequalityOrderIndex = -1;

		for (int i = 0; i < order.size(); i += 1) {
			ObjectifyQueryOrdering ordering = order.get(i);

			if (ordering.getField().equals(inequalityField)) {
				inequalityOrderIndex = i;
				break;
			}
		}

		if (inequalityOrderIndex != -1) {
			ObjectifyQueryOrdering firstOrdering = order.get(inequalityOrderIndex);
			order.remove(inequalityOrderIndex);

			Chain<ObjectifyQueryOrdering> chain = new ObjectifyQueryOrderingChainImpl(firstOrdering);

			for (ObjectifyQueryOrdering ordering : order) {
				if (ordering != null) {
					chain.chain(ordering);
				}
			}

			this.setResultsOrdering(chain);
		}
	}

	/**
	 * Asserts the proper filter order. Should be called after
	 * {{@link #tryReorderChain()}.
	 */
	private void assertProperFilterOrder() throws InvalidQuerySortingException {
		if (this.resultsOrdering != null) {
			if (this.queryFilters.isEmpty() == false) {
				ObjectifyQueryFilter queryFilter = this.queryFilters.get(0);
				String field = queryFilter.getField();

				for (ObjectifyQueryOrdering ordering : this.resultsOrdering) {
					if (ordering.getField().equals(field)) {
						return;	// Is valid.
					}
				}
				
				// This isn't always the case..?
				//throw new InvalidQuerySortingException("Missing sorting for primary filter.");
			}
			
			// Can order without a query filter.
		}

		// No ordering.
	}

	@Override
	public ObjectifyQueryRequestBuilder<T> copyBuilder() {
		ObjectifyQueryRequestBuilderImpl<T> builder = new ObjectifyQueryRequestBuilderImpl<T>(this.queryService);

		builder.setQueryFilters(this.queryFilters);
		builder.setSimpleQueryFilters(this.simpleQueryFilters);
		builder.setResultsOrdering(this.resultsOrdering);
		builder.setOptions(new ObjectifyQueryRequestOptionsImpl(this.options));

		return builder;
	}

	@Override
	public ExecutableObjectifyQuery<T> buildExecutableQuery() throws InvalidQuerySortingException {
		this.tryReorderChain();
		this.assertProperFilterOrder();
		ObjectifyQueryRequestBuilder<T> copy = this.copyBuilder();
		return new ExecutableObjectifyQueryImpl<T>(this.queryService, copy, this.options);
	}

	@Override
	public String toString() {
		return "ObjectifyQueryRequestBuilderImpl [queryService=" + this.queryService + ", queryFilters="
		        + this.queryFilters + ", simpleQueryFilters=" + this.simpleQueryFilters + ", options=" + this.options
		        + ", resultsOrdering=" + this.resultsOrdering + "]";
	}

}
