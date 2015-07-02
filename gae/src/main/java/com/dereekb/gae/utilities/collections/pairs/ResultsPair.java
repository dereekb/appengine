package com.dereekb.gae.utilities.collections.pairs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Abstract object pair.
 *
 * @author dereekb
 *
 * @param <S>
 *            "Source" object. The object being used to influence the other object.
 * @param <R>
 *            "Results" object. The object being edited generally.
 */
public abstract class ResultsPair<S, R> extends HandlerPair<S, R> {

	private ResultsPairState state = ResultsPairState.NEW;

	/**
	 * Source object to use for generating the results.
	 *
	 * @param source
	 */
	public ResultsPair(S source) {
		super(source, null);
	}

	public S getSource() {
		return this.key;
	}

	public R getResult() {
		return this.object;
	}

	public ResultsPairState getState() {
		return this.state;
	}

	public boolean hasResult() {
		return (this.object != null);
	}

	/**
	 * Shortcut for calling {@link #setResult(Object)} using null.
	 */
	public void flagFailure() {
		this.setResult(null);
	}

	public void setResult(R result) {
		if (this.object == null) {
			if (result != null) {
				this.object = result;
				this.state = ResultsPairState.SUCCESS;
			} else {
				this.state = ResultsPairState.FAILURE;
			}
		} else {
			if (result != null) {
				this.object = result;
				this.state = ResultsPairState.REPLACED;
			} else {
				this.clearResult();
			}
		}
	}

	public void clearResult() {
		if (this.object != null) {
			this.object = null;
			this.state = ResultsPairState.CLEARED;
		}
	}

	public static <S, R> List<S> getSources(Iterable<? extends ResultsPair<S, R>> pairs) {
		return HandlerPair.getKeys(pairs);
	}

	public static <S, R> List<R> getResults(Iterable<? extends ResultsPair<S, R>> pairs) {
		return HandlerPair.getObjects(pairs);
	}

	@Override
	public String toString() {
		return "ResultsPair [source=" + this.key + ", result=" + this.object + "]";
	}

	public static <S, R, P extends ResultsPair<S, R>> List<P> pairsWithResults(Iterable<P> pairs) {
		Set<ResultsPairState> states = new HashSet<ResultsPairState>();
		states.add(ResultsPairState.SUCCESS);
		states.add(ResultsPairState.REPLACED);
		return pairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultsPair<S, R>> List<P> pairsWithoutResults(Iterable<P> pairs) {
		Set<ResultsPairState> states = new HashSet<ResultsPairState>();
		states.add(ResultsPairState.NEW);
		states.add(ResultsPairState.CLEARED);
		states.add(ResultsPairState.FAILURE);
		return pairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultsPair<S, R>> List<P> pairsWithState(Iterable<P> pairs,
	                                                                         ResultsPairState state) {
		List<P> withState = new ArrayList<P>();

		for (P pair : pairs) {
			if (pair.getState() == state) {
				withState.add(pair);
			}
		}

		return withState;
	}

	public static <S, R, P extends ResultsPair<S, R>> List<P> pairsWithStates(Iterable<P> pairs,
	                                                                          Set<ResultsPairState> states) {
		List<P> withState = new ArrayList<P>();

		for (P pair : pairs) {
			ResultsPairState state = pair.getState();

			if (states.contains(state)) {
				withState.add(pair);
			}
		}

		return withState;
	}

	public static <S, R, P extends ResultsPair<S, R>> HashMapWithList<FilterResult, P> filterSuccessfulPairs(Iterable<P> pairs) {
		Set<ResultsPairState> states = new HashSet<ResultsPairState>();
		states.add(ResultsPairState.SUCCESS);
		states.add(ResultsPairState.REPLACED);
		return filterPairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultsPair<S, R>> HashMapWithList<FilterResult, P> filterFailedPairs(Iterable<P> pairs) {
		Set<ResultsPairState> states = new HashSet<ResultsPairState>();
		states.add(ResultsPairState.NEW);
		states.add(ResultsPairState.CLEARED);
		states.add(ResultsPairState.FAILURE);
		return filterPairsWithStates(pairs, states);
	}

	public static <S, R, P extends ResultsPair<S, R>> HashMapWithList<FilterResult, P> filterPairsWithStates(Iterable<P> pairs,
	                                                                                                         Set<ResultsPairState> states) {
		HashMapWithList<FilterResult, P> map = new HashMapWithList<FilterResult, P>();

		for (P pair : pairs) {
			ResultsPairState state = pair.getState();

			if (states.contains(state)) {
				map.add(FilterResult.PASS, pair);
			} else {
				map.add(FilterResult.FAIL, pair);
			}
		}

		return map;
	}

	public static <S, R, P extends ResultsPair<S, R>> HashMapWithList<ResultsPairState, P> pairsByState(Iterable<P> pairs) {
		HashMapWithList<ResultsPairState, P> map = new HashMapWithList<ResultsPairState, P>();

		for (P pair : pairs) {
			map.add(pair.getState(), pair);
		}

		return map;
	}

	/**
	 * Generates a map of each results pair keyed by their own key.
	 *
	 * @param pairs
	 * @return Map keyed by {@link ResultsPair} key, with values of
	 *         {@link ResultsPair}.
	 */
	public static <S, R, P extends ResultsPair<S, R>> Map<S, P> pairsKeyMap(Iterable<P> pairs) {
		Map<S, P> map = new HashMap<S, P>();

		for (P pair : pairs) {
			S key = pair.getKey();
			map.put(key, pair);
		}

		return map;
	}

	/**
	 * Generates a map that shows which {@link ResultsPair} have results and
	 * those that do not.
	 *
	 * @param pairs
	 * @return {@link HashMapWithList} keyed by whether or not results are
	 *         contained, and lists of pairs for each.
	 */
	public static <S, R, P extends ResultsPair<S, R>> HashMapWithList<Boolean, P> containsResultsMap(Iterable<P> pairs) {
		HashMapWithList<Boolean, P> map = new HashMapWithList<Boolean, P>();

		for (P pair : pairs) {
			map.add(pair.hasResult(), pair);
		}

		return map;
	}

	/**
	 * Generates a map that shows which {@link ResultsPair} keys have generated
	 * results.
	 *
	 * @param pairs
	 * @return {@link HashMapWithList} keyed by whether or not results are
	 *         contained, and lists of keys for each.
	 */
	public static <S, R, P extends ResultsPair<S, R>> HashMapWithList<Boolean, S> successfulKeysMap(Iterable<P> pairs) {
		HashMapWithList<Boolean, S> map = new HashMapWithList<Boolean, S>();

		for (P pair : pairs) {
			map.add(pair.hasResult(), pair.getKey());
		}

		return map;
	}

}
