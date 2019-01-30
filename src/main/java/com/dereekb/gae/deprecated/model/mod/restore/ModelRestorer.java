package com.thevisitcompany.gae.deprecated.model.mod.restore;

import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.utilities.collections.SingleItem;
import com.thevisitcompany.gae.utilities.collections.batch.BatchGenerator;

/**
 * Class for restoring objects. Breaks up the collection of items into batches to be restored.
 * 
 * @author dereekb
 *
 * @param <T> 
 */
public class ModelRestorer<T> {

	private ModelRestoreDelegate<T> delegate;
	private Integer batchSize = 500;

	public ModelRestorer() {
		super();
	}

	public ModelRestorer(ModelRestoreDelegate<T> delegate) {
		super();
		this.delegate = delegate;
	}

	public void restore(T item) {
		SingleItem<T> singleItem = SingleItem.withValue(item);
		delegate.restore(singleItem);
	}

	/**
	 * Restores a collection of items of type <T>.
	 * 
	 * @param items
	 * @return
	 */
	public void restore(Collection<T> items) {
		BatchGenerator<T> batchGenerator = new BatchGenerator<T>(this.batchSize);
		List<List<T>> batches = batchGenerator.createBatchesWithCollection(items);

		for (List<T> batch : batches) {
			delegate.restore(batch);
		}
	}

	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}

	public ModelRestoreDelegate<T> getDelegate() {
		return delegate;
	}

	public void setDelegate(ModelRestoreDelegate<T> delegate) {
		this.delegate = delegate;
	}

}
