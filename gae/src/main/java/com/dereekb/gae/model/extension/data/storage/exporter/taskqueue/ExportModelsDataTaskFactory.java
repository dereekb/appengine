package com.dereekb.gae.model.extension.data.storage.exporter.taskqueue;

import com.dereekb.gae.model.extension.data.storage.exporter.Exporter;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

@Deprecated
public class ExportModelsDataTaskFactory<T>
        implements Factory<ExportModelsDataTask<T>> {

	private Exporter<T> sharedExporter;
	private ExportModelsDataTaskDelegate sharedDelegate;

	@Override
	public ExportModelsDataTask<T> make() throws FactoryMakeFailureException {

		if (this.sharedExporter == null) {
			throw new FactoryMakeFailureException("Null Exporter.");
		}

		if (this.sharedDelegate == null) {
			throw new FactoryMakeFailureException("Null Delegate.");
		}

		ExportModelsDataTask<T> task = new ExportModelsDataTask<T>(this.sharedDelegate, this.sharedExporter);
		return task;
	}

	public Exporter<T> getSharedExporter() {
		return this.sharedExporter;
	}

	public void setSharedExporter(Exporter<T> sharedExporter) {
		this.sharedExporter = sharedExporter;
	}

	public ExportModelsDataTaskDelegate getSharedDelegate() {
		return this.sharedDelegate;
	}

	public void setSharedDelegate(ExportModelsDataTaskDelegate sharedDelegate) {
		this.sharedDelegate = sharedDelegate;
	}

}
