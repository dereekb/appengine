package com.dereekb.gae.server.storage.upload.reader;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.gcs.deprecated.blobstore.upload.UploadedBlobFile;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * Represents a set of {@link UploadedBlobFile} values from an
 * {@link HttpServletRequest}.
 *
 * <p>
 * Provides several ways to access the data and a way to delete any
 * </p>
 *
 * @author dereekb
 *
 */
public interface UploadedFileSet {

	/**
	 * @return {@link Set} of all uploaded file names.
	 */
	public Set<String> getUploadedFileNames();

	/**
	 * @return {@link HashMapWithList} of all {@link UploadedFile} values keyed
	 *         by http file name. Never {@code null}.
	 */
	public HashMapWithList<String, UploadedFile> getUploadedFilesMap();

	/**
	 * @return {@link List} of all {@link UploadedFile} values. Never
	 *         {@code null}.
	 */
	public List<UploadedFile> getAllUploadedFiles();

	/**
	 * Deletes all data uploaded in the request, if applicable.
	 */
	public void deleteSetData();

}
