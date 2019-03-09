package com.dereekb.gae.model.extension.search.document.index;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;

/**
 * Indexes a model. Index change is based on the {@link IndexAction} of this
 * pair.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IndexPair<T extends UniqueSearchModel> extends SuccessResultsPair<T>
        implements UniqueSearchModel {

	private boolean wasChanged = false;
	private final boolean wasIndexed;
	private final IndexAction action;

	public IndexPair(T model, IndexAction action) {
		super(model);
		this.action = action;
		this.wasIndexed = this.isIndexed();
	}

	/**
	 * {@code static} function for generating a {@link List} of
	 * {@link IndexPair} values, all with the same {@link IndexAction}.
	 *
	 * @param input
	 *            {@link Iterable} set of models
	 * @param action
	 *            {@link IndexAction} value
	 * @return {@link List} of {@link IndexPair} values. Never {@code null}.
	 */
	public static <T extends UniqueSearchModel> List<IndexPair<T>> makePairs(Iterable<T> input,
	                                                                         IndexAction action) {
		List<IndexPair<T>> pairs = new ArrayList<>();

		for (T element : input) {
			IndexPair<T> pair = new IndexPair<>(element, action);
			pairs.add(pair);
		}

		return pairs;
	}

	public T getModel() {
		return this.key;
	}

	/**
	 * @return Whether or not the model has a search identifier attached to it.
	 */
	public boolean isIndexed() {
		return this.key.getSearchIdentifier() != null;
	}

	/**
	 * @return Whether or not the model was indexed initially, when the
	 *         IndexPair was generated.
	 */
	public boolean wasInitiallyIndexed() {
		return this.wasIndexed;
	}

	/**
	 * The model is only considered as "changed" if setSearchIdentifier() is
	 * called. Direct changes against the model are ignored.
	 *
	 * This is available because comparing wasInitiallyIndexed() to isIndexed()
	 * would fail in cases where the identifier was changed to something else.
	 *
	 * @return True if the model had it's search identifier changed via the
	 *         setSearchIdentifier() function.
	 */
	public boolean modelWasChange() {
		return this.wasChanged;
	}

	public IndexAction getAction() {
		return this.action;
	}

	// MARK: Unique Search Model
	@Override
	public String getSearchIdentifier() {
		return this.key.getSearchIdentifier();
	}

	@Override
	public void setSearchIdentifier(String identifier) {
		this.key.setSearchIdentifier(identifier);
		this.wasChanged = true;
	}

}
