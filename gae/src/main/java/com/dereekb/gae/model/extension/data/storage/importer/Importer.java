package com.dereekb.gae.model.extension.data.storage.importer;

import java.util.Collection;

import com.dereekb.gae.server.storage.file.StorageFile;

/**
 * Imports models from saved data.
 *
 * @author dereekb
 *
 * @param <T>
 */
public interface Importer<T> {

	/**
	 * Imports the object collection stored in a file.
	 *
	 * @param object
	 *            Objects to import.
	 * @param file
	 *            Output File
	 */
	public Collection<T> importObjects(StorageFile file) throws ImportException;

}
