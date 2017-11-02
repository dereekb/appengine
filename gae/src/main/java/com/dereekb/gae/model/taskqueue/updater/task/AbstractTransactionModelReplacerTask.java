package com.dereekb.gae.model.taskqueue.updater.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.datastore.Deleter;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.GetterSetter;
import com.dereekb.gae.server.datastore.Storer;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelKeyListAccessorTask;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility.PartitionDelegate;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.googlecode.objectify.Work;

/**
 * Abstract {@link AbstractModelKeyListAccessorTask} implementation that reads
 * and converts/replaces the target models using a delegate, objectify/google
 * app engine
 * transactions, and an {@link Storer}.
 * <p>
 * This is a basic implementation that will not update relations and states
 * between other models, so usage is limited in some situations.
 * 
 * @author dereekb
 *
 * @param <I>
 *            model type
 */
public abstract class AbstractTransactionModelReplacerTask<I extends UniqueModel, O extends UniqueModel> extends AbstractModelKeyListAccessorTask<I> {

	private Getter<I> getter;
	private Deleter<I> deleter;
	private Storer<O> storer;

	public AbstractTransactionModelReplacerTask(GetterSetter<I> getterSetter, Storer<O> storer) {
		this(getterSetter, getterSetter, storer);
	}
	
	public AbstractTransactionModelReplacerTask(Getter<I> getter, Deleter<I> deleter, Storer<O> storer) {
		super();
		this.setGetter(getter);
		this.setDeleter(deleter);
		this.setStorer(storer);
	}

	public Getter<I> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<I> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	public Deleter<I> getDeleter() {
		return this.deleter;
	}

	public void setDeleter(Deleter<I> deleter) {
		if (deleter == null) {
			throw new IllegalArgumentException("deleter cannot be null.");
		}

		this.deleter = deleter;
	}

	public Storer<O> getStorer() {
		return this.storer;
	}

	public void setStorer(Storer<O> storer) {
		if (storer == null) {
			throw new IllegalArgumentException("storer cannot be null.");
		}

		this.storer = storer;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<I> input) throws FailedTaskException {
		final PartitionDelegate<ModelKey, List<ReplacementPair<O>>> delegate = this.makeReplacerPartitionDelegate();

		List<List<ReplacementPair<O>>> results = null;

		try {
			results = ObjectifyTransactionUtility.transact().doTransactionWithPartition(input, delegate);
		} catch (RuntimeException e) {
			throw new FailedTaskException(e);
		}

		List<ReplacementPair<O>> converted = ListUtility.flatten(results);
		
		List<O> modelToStore = ReplacementPair.getReplacements(converted);
		List<ModelKey> modelsToDelete = ReplacementPair.getDeletable(converted);

		this.storeModels(modelToStore);
		this.deleteModels(modelsToDelete);
	}

	protected abstract PartitionDelegate<ModelKey, List<ReplacementPair<O>>> makeReplacerPartitionDelegate();

	protected abstract class PartitionDelegateImpl
	        implements PartitionDelegate<ModelKey, List<ReplacementPair<O>>> {

		@Override
		public Work<List<ReplacementPair<O>>> makeWorkForInput(final Iterable<ModelKey> input) {
			return new Work<List<ReplacementPair<O>>>() {

				@Override
				public List<ReplacementPair<O>> run() {
					List<I> models = AbstractTransactionModelReplacerTask.this.getModels(input);
					Map<ModelKey, I> modelsMap = ModelKey.makeModelKeyMap(models);

					List<ReplacementPair<O>> pairs = new ArrayList<ReplacementPair<O>>();

					for (ModelKey key : input) {
						ReplacementPair<O> pair = new ReplacementPair<O>(key);
						I model = modelsMap.get(key);

						if (model == null) {
							pair.setExists(false);
						} else {
							try {
								O convert = PartitionDelegateImpl.this.convertModel(model);
								pair.setReplacement(convert);
							} catch (SkipReplacementException e) {
								pair.setShouldDelete(e.isShouldDelete());
							}
						}
						
						pairs.add(pair);
					}

					return pairs;
				}

			};
		}

		protected abstract O convertModel(I model) throws SkipReplacementException;

	}

	protected List<I> getModels(Iterable<ModelKey> keys) {
		return AbstractTransactionModelReplacerTask.this.getter.getWithKeys(keys);
	}

	protected void storeModels(Iterable<O> newModels) {
		AbstractTransactionModelReplacerTask.this.storer.store(newModels);
	}

	protected void deleteModels(Iterable<ModelKey> keys) {
		AbstractTransactionModelReplacerTask.this.deleter.deleteWithKeys(keys);
	}

	protected static class ReplacementPair<O> extends HandlerPair<ModelKey, O> {

		private boolean shouldDelete = true;
		private boolean exists = true;

		public ReplacementPair(ModelKey input) {
			super(input, null);
		}

		public void setReplacement(O replacement) {
			this.object = replacement;
		}

		public boolean isShouldDelete() {
			return this.shouldDelete;
		}

		public void setShouldDelete(boolean shouldDelete) {
			this.shouldDelete = shouldDelete;
		}

		public boolean isExists() {
			return this.exists;
		}

		public void setExists(boolean exists) {
			this.exists = exists;

			if (exists == false) {
				this.shouldDelete = false;
			}
		}

		public static <O> List<O> getReplacements(List<ReplacementPair<O>> pairs) {
			return HandlerPair.getObjects(pairs);
		}

		public static <O> List<ModelKey> getDeletable(List<ReplacementPair<O>> pairs) {
			List<ModelKey> keys = new ArrayList<ModelKey>();
			
			for (ReplacementPair<O> pair : pairs) {
				if (pair.isShouldDelete()) {
					keys.add(pair.getKey());
				}
			}
			
			return keys;
		}

	}

	protected static class SkipReplacementException extends Exception {

		private static final long serialVersionUID = 1L;

		private final boolean shouldDelete;

		public SkipReplacementException(boolean shouldDelete) {
			this.shouldDelete = shouldDelete;
		}

		public boolean isShouldDelete() {
			return this.shouldDelete;
		}

	}

}
