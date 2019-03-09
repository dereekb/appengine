package com.dereekb.gae.model.extension.data.storage.exporter;

import java.util.Collection;

import com.dereekb.gae.server.storage.object.file.impl.StorableFileImpl;

/**
 * Interface for exporting objects.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Exporter<T> {

	/**
	 * Exports the object collection to the specified file.
	 *
	 * @param objects
	 *            Objects to export.
	 * @param file
	 *            Output File
	 * @throws ExportException
	 *             if the export fails.
	 */
	public void exportObjects(Collection<T> objects,
	                          StorableFileImpl file)
	        throws ExportException;

}
