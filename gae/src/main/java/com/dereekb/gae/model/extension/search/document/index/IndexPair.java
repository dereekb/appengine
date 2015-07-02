package com.dereekb.gae.model.extension.search.document.index;

import com.dereekb.gae.server.search.UniqueSearchModel;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;

/**
 * Indexes a model. Index change is based on the {@link IndexAction} of this
 * pair.
 *
 * @author dereekb
 *
 * @param <T>
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

	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		return this.key;
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
