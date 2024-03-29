package com.dereekb.gae.utilities.collections.pairs.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithSet;
import com.dereekb.gae.utilities.collections.pairs.MutableResultPair;
import com.dereekb.gae.utilities.collections.pairs.ResultPair;
import com.dereekb.gae.utilities.collections.pairs.ResultPairState;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Abstract {@link MutableResultPair} implementation.
 *
 * @author dereekb
 *
 * @param <S>
 *            "Source" object. The object being used to influence the other
 *            object.
 * @param <R>
 *            "Results" object. The object being edited generally.
 */
public abstract class ResultPairImpl<S, R> extends HandlerPair<S, R> implements MutableResultPair<S, R> {

	private ResultPairState state = ResultPairState.NEW;

	/**
	 * Source object to use for generating the results.
	 *
	 * @param source
	 */
	public ResultPairImpl(S source) {
		super(source, null);
	}

	public S getSource() {
		return this.key;
	}

	public R getResult() {
		return this.object;
	}

	@Override
	public ResultPairState getState() {
		return this.state;
	}

	@Override
	public boolean hasResult() {
		return (this.object != null);
	}

	/**
	 * Shortcut for calling {@link #setResult(Object)} using null.
	 */
	@Override
	public void flagFailure() {
		this.setResult(null);
	}

	@Override
	public void setResult(R result) {
		R previous = this.object;

		this.object = result;
		this.state = this.recalculateState(this.object, previous);
	}
	
	protected ResultPairState recalculateState(R newValue, R oldValue) {
		if (oldValue == null) {
			if (newValue != null) {
				return ResultPairState.SUCCESS;
			} else {
				return ResultPairState.FAILURE;
			}
		} else {
			if (newValue != null) {
				return ResultPairState.REPLACED;
			} else {
				return ResultPairState.CLEARED;
			}
		}
	}

	@Override
	public final void clearResult() {
		if (this.object != null) {
			this.object = null;
			this.state = ResultPairState.CLEARED;
		}
	}

	@Override
	public String toString() {
		return "ResultsPair [source=" + this.key + ", result=" + this.object + "]";
	}

	// MARK: Utility
	public static <S, R> List<S> getSources(Iterable<? extends ResultPair<S, R>> pairs) {
		return HandlerPair.getKeys(pairs);
	}

	public static <S, R> List<R> getResults(Iterable<? extends ResultPair<S, R>> pairs) {
		return HandlerPair.getObjects(pairs);
	}

	public static <S, R, P extends ResultPair<S, R>> List<P> pairsWithResults(Iterable<P> pairs) {
		Set<ResultPairState> states = new HashSet<ResultPairState>();
		states.add(ResultPairState.SUCCESS);
		states.add(ResultPairState.REPLACED);
		return pairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultPair<S, R>> List<P> pairsWithoutResults(Iterable<P> pairs) {
		Set<ResultPairState> states = new HashSet<ResultPairState>();
		states.add(ResultPairState.NEW);
		states.add(ResultPairState.CLEARED);
		states.add(ResultPairState.FAILURE);
		return pairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultPair<S, R>> List<P> pairsWithState(Iterable<P> pairs,
	                                                                         ResultPairState state) {
		List<P> withState = new ArrayList<P>();

		for (P pair : pairs) {
			if (pair.getState() == state) {
				withState.add(pair);
			}
		}

		return withState;
	}

	public static <S, R, P extends ResultPair<S, R>> List<P> pairsWithStates(Iterable<P> pairs,
	                                                                          Set<ResultPairState> states) {
		List<P> withState = new ArrayList<P>();

		for (P pair : pairs) {
			ResultPairState state = pair.getState();

			if (states.contains(state)) {
				withState.add(pair);
			}
		}

		return withState;
	}

	public static <S, R, P extends ResultPair<S, R>> HashMapWithList<FilterResult, P> filterSuccessfulPairs(Iterable<P> pairs) {
		Set<ResultPairState> states = new HashSet<ResultPairState>();
		states.add(ResultPairState.SUCCESS);
		states.add(ResultPairState.REPLACED);
		return filterPairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultPair<S, R>> HashMapWithList<FilterResult, P> filterFailedPairs(Iterable<P> pairs) {
		Set<ResultPairState> states = new HashSet<ResultPairState>();
		states.add(ResultPairState.NEW);
		states.add(ResultPairState.CLEARED);
		states.add(ResultPairState.FAILURE);
		return filterPairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultPair<S, R>> HashMapWithList<FilterResult, P> filterPairsWithStates(Iterable<P> pairs,
	                                                                                                         Set<ResultPairState> states) {
		HashMapWithList<FilterResult, P> map = new HashMapWithList<FilterResult, P>();

		for (P pair : pairs) {
			ResultPairState state = pair.getState();

			if (states.contains(state)) {
				map.add(FilterResult.PASS, pair);
			} else {
				map.add(FilterResult.FAIL, pair);
			}
		}

		return map;
	}

	public static <S, R, P extends ResultPair<S, R>> HashMapWithList<ResultPairState, P> pairsByState(Iterable<P> pairs) {
		HashMapWithList<ResultPairState, P> map = new HashMapWithList<ResultPairState, P>();

		for (P pair : pairs) {
			map.add(pair.getState(), pair);
		}

		return map;
	}

	/**
	 * Generates a map of each results pair keyed by their own key.
	 *
	 * @param pairs
	 * @return Map keyed by {@link ResultPair} key, with values of
	 *         {@link ResultPair}.
	 */
	public static <S, R, P extends ResultPair<S, R>> Map<S, P> pairsKeyMap(Iterable<P> pairs) {
		Map<S, P> map = new HashMap<S, P>();

		for (P pair : pairs) {
			S key = pair.getKey();
			map.put(key, pair);
		}

		return map;
	}

	/**
	 * Generates a map that maps all result objects to the input key objects.
	 *
	 * @param pairs
	 * @return {@link HashMapWithList} keyed by whether or not results are
	 *         contained, and lists of pairs for each.
	 */
	public static <S, R, P extends ResultPair<S, R>> HashMapWithSet<R, S> keysResultsMap(Iterable<P> pairs) {
		HashMapWithSet<R, S> map = new HashMapWithSet<R, S>();

		for (P pair : pairs) {
			map.add(pair.getObject(), pair.getKey());
		}

		return map;
	}

	/**
	 * Generates a map that shows which {@link ResultPair} have results and
	 * those that do not.
	 *
	 * @param pairs
	 * @return {@link HashMapWithList} keyed by whether or not results are
	 *         contained, and lists of pairs for each.
	 */
	public static <S, R, P extends ResultPair<S, R>> HashMapWithList<Boolean, P> containsResultsMap(Iterable<P> pairs) {
		HashMapWithList<Boolean, P> map = new HashMapWithList<Boolean, P>();

		for (P pair : pairs) {
			map.add(pair.hasResult(), pair);
		}

		return map;
	}

	/**
	 * Generates a map that shows which {@link ResultPair} keys have generated
	 * results.
	 *
	 * @param pairs
	 * @return {@link HashMapWithList} keyed by whether or not results are
	 *         contained, and lists of keys for each.
	 */
	public static <S, R, P extends ResultPair<S, R>> HashMapWithList<Boolean, S> successfulKeysMap(Iterable<P> pairs) {
		HashMapWithList<Boolean, S> map = new HashMapWithList<Boolean, S>();

		for (P pair : pairs) {
			map.add(pair.hasResult(), pair.getKey());
		}

		return map;
	}

}
