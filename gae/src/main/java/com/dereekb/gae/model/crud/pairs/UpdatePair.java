package com.dereekb.gae.model.crud.pairs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.dereekb.gae.utilities.collections.pairs.impl.ResultsPair;
import com.dereekb.gae.utilities.collections.pairs.impl.SuccessResultsPair;
import com.dereekb.gae.web.api.util.attribute.exception.InvalidAttributeException;

/**
 * A pair containing and target and a template to process an edit with.
 *
 * The {@link ResultsPair} key is the target.
 *
 * @author dereekb
 *
 * @param <T>
 *            Type of target being edited and used as a template.
 */
public class UpdatePair<T extends UniqueModel> extends SuccessResultsPair<T> {

	private final T template;
	private InvalidAttributeException failureException;

	/**
	 * @param target
	 *            Object being edited.
	 * @param template
	 *            Template being used to edit.
	 */
	public UpdatePair(T target, T template) {
		super(target);
		this.template = template;
	}

	private UpdatePair(HandlerPair<T, T> pair) {
		super(pair.getKey());
		this.template = pair.getObject();
	}

	public T getTarget() {
		return this.key;
	}

	public T getTemplate() {
		return this.template;
	}

	public InvalidAttributeException getFailureException() {
		return this.failureException;
	}

	public void setFailureException(InvalidAttributeException failureException) {
		this.setSuccessful(false);
		this.failureException = failureException;
	}

	// MARK: Utility
	/**
	 * Creates a new set of update pairs. Pairs up the same index in each input
	 * collection.
	 *
	 * @param targets
	 *            Objects to edit.
	 * @param templates
	 *            Templates to use for editing.
	 * @return
	 * @throws IllegalArgumentException
	 *             If targets size does not equal templates size.
	 */
	public static <T extends UniqueModel> List<UpdatePair<T>> makePairs(Collection<T> targets,
	                                                                    Collection<T> templates)
	        throws IllegalArgumentException {
		List<HandlerPair<T, T>> pairs = ModelKey.makePairs(targets, templates);
		pairs = HandlerPair.filterRepeatingKeys(pairs);

		List<UpdatePair<T>> updatePairs = new ArrayList<>();
		for (HandlerPair<T, T> pair : pairs) {
			UpdatePair<T> updatePair = new UpdatePair<>(pair);
			updatePairs.add(updatePair);
		}

		return updatePairs;
	}

}