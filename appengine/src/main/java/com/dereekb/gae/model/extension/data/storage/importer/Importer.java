package com.dereekb.gae.model.extension.data.storage.importer;

import java.util.Collection;

import com.dereekb.gae.server.storage.object.file.StorableFile;

/**
 * Imports models from saved data.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface Importer<T> {

	/**
	 * Imports objects from the referenced file.
	 * 
	 * @param file
	 *            {@link StorableFile}. Never {@code null}.
	 * @return {@link Collection} of objects imported. Never {@code null}.
	 * @throws ImportException
	 *             thrown if the import fails.
	 */
	public Collection<T> importObjects(StorableFile file) throws ImportException;

}
