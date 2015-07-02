package com.dereekb.gae.model.extension.search.query.search;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;

/**
 * Pair used by {@link ObjectifyQueryFunction}.
 *
 * @author dereekb
 *
 * @param <Q>
 *            Query type.
 */
public class QueryPair<Q>
        implements StagedFunctionObject<Q> {

	private final Q query;
	private List<ModelKey> keyResults;

	public QueryPair(Q query) {
		this.query = query;
	}

	public List<ModelKey> getKeyResults() {
		return this.keyResults;
	}

	public void setKeyResults(List<ModelKey> keyResults) {
		this.keyResults = keyResults;
	}

	public Q getQuery() {
		return this.query;
	}

	@Override
	public Q getFunctionObject(StagedFunctionStage stage) {
		return this.query;
	}

}