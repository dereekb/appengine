package com.dereekb.gae.model.extension.data.storage.exporter.taskqueue;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.data.storage.exporter.ExportException;
import com.dereekb.gae.model.extension.data.storage.exporter.Exporter;
import com.dereekb.gae.model.extension.taskqueue.api.CustomTaskInfo;
import com.dereekb.gae.model.extension.taskqueue.task.iterate.IterateModelsSubtask;
import com.dereekb.gae.server.storage.file.impl.StorableFileImpl;

/**
 * Exporter subtask.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class ExportModelsDataTask<T>
        implements IterateModelsSubtask<T> {

	private final ExportModelsDataTaskDelegate delegate;
	private final Exporter<T> exporter;

	private StorableFileImpl file;
	private List<T> models = new ArrayList<T>();

	public ExportModelsDataTask(ExportModelsDataTaskDelegate delegate, Exporter<T> exporter) {
		super();
		this.delegate = delegate;
		this.exporter = exporter;
	}

	@Override
	public void initTask(CustomTaskInfo request) {
		this.file = this.delegate.storageFileForRequest(request);
	}

	@Override
	public void useModelBatch(List<T> batch) {
		this.models.addAll(batch);
	}

	@Override
	public void endTask() {
		try {
			this.exporter.exportObjects(this.models, this.file);
		} catch (ExportException e) {
			throw new RuntimeException("Failed exporting models.", e);
		}
	}

	public Exporter<T> getExporter() {
		return this.exporter;
	}

	public ExportModelsDataTaskDelegate getDelegate() {
		return this.delegate;
	}

}
