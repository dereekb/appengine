package com.dereekb.gae.server.datastore.objectify.query.order.impl;

import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrderingChain;
import com.dereekb.gae.utilities.collections.chain.Chain;
import com.dereekb.gae.utilities.collections.chain.impl.AbstractChainImpl;

/**
 * {@link Chain} of {@link ObjectifyQueryOrdering} values.
 *
 * @author dereekb
 *
 */
public class ObjectifyQueryOrderingChainImpl extends AbstractChainImpl<ObjectifyQueryOrdering, ObjectifyQueryOrderingChainImpl>
        implements ObjectifyQueryOrderingChain {

	public ObjectifyQueryOrderingChainImpl(ObjectifyQueryOrdering value) {
		super(value);
	}

	protected ObjectifyQueryOrderingChainImpl(ObjectifyQueryOrdering value, ObjectifyQueryOrderingChainImpl next) {
		super(value, next);
	}

	@Override
	protected ObjectifyQueryOrderingChainImpl make(ObjectifyQueryOrdering element,
	                                               ObjectifyQueryOrderingChainImpl next) {
		return new ObjectifyQueryOrderingChainImpl(element, next);
	}

	@Override
	protected ObjectifyQueryOrderingChainImpl self() {
		return this;
	}

}
