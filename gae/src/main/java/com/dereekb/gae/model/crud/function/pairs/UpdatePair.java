package com.dereekb.gae.model.crud.function.pairs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.dereekb.gae.model.crud.function.exception.AttributeFailureException;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.collections.pairs.ResultsPair;
import com.dereekb.gae.utilities.collections.pairs.SuccessResultsPair;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.function.staged.components.StagedFunctionStage;

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
public class UpdatePair<T extends UniqueModel> extends SuccessResultsPair<T>
        implements StagedFunctionObject<T> {

	private final T template;
	private AttributeFailureException failureException;

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

	public T getTarget() {
		return this.key;
	}

	public T getTemplate() {
		return this.template;
	}

	public AttributeFailureException getFailureException() {
		return this.failureException;
	}

	/**
	 * Sets the failure exception and the success for this pair to false.
	 *
	 * @param failureException
	 */
	public void setFailureException(AttributeFailureException failureException) {
		this.setSuccessful(false);
		this.failureException = failureException;
	}

	/**
	 * Returns the source only at the started stage in order to allow for pre-function validation and filtering on the
	 * key.
	 */
	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		T target = null;

		if (stage.after(StagedFunctionStage.FUNCTION_RUNNING)) {
			target = this.getTarget();
		} else {
			target = this.getTemplate();
		}

		return target;
	}

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
	                                                Collection<T> templates) throws IllegalArgumentException {
		if (targets.size() != templates.size()) {
			throw new IllegalArgumentException("Must have the same amount of targets and templates.");
		}

		List<UpdatePair<T>> pairs = new ArrayList<UpdatePair<T>>();
		Iterator<T> templatesIterator = templates.iterator();

		for (T target : targets) {
			T template = templatesIterator.next();
			UpdatePair<T> pair = new UpdatePair<T>(target, template);
			pairs.add(pair);
		}

		return pairs;
	}

}