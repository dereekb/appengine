package com.dereekb.gae.server.storage.upload.reader.impl;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.storage.upload.reader.UploadedFileReader;
import com.dereekb.gae.server.storage.upload.reader.UploadedFileSet;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * {@link UploadedFileReader} implementation for the Blobstore.
 *
 * @author dereekb
 *
 */
public class UploadedBlobFileReader
        implements UploadedFileReader {

	public UploadedBlobFileReader() {}

	// MARK: UploadedFileReader
	@Override
	public UploadedFileSet readUploadedFiles(HttpServletRequest request) {
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		return new UploadedBlobFileSet(blobstoreService, request);
	}

	@Override
	public String toString() {
		return "UploadedBlobFileReader []";
	}

}
