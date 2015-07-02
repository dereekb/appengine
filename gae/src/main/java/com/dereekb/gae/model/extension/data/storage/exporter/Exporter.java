package com.dereekb.gae.model.extension.data.storage.exporter;

import java.util.Collection;

import com.dereekb.gae.server.storage.file.StorageFile;

/**
 * Interface for exporting objects.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface Exporter<T> {

	/**
	 * Exports the object collection to the specified file.
	 *
	 * @param object
	 *            Objects to export.
	 * @param file
	 *            Output File
	 */
	public void exportObjects(Collection<T> objects,
	                          StorageFile file) throws ExportException;

}
