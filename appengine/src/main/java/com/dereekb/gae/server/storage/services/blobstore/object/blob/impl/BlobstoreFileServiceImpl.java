package com.dereekb.gae.server.storage.services.blobstore.object.blob.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.dereekb.gae.server.storage.services.blobstore.object.blob.BlobstoreFileService;
import com.dereekb.gae.server.storage.services.blobstore.object.blob.BlobstoreKeyService;
import com.dereekb.gae.server.storage.services.gcs.object.file.GcsStorableFile;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Helper class for using the blobstore indexService to serve blobs.
 *
 * @author dereekb
 */
public class BlobstoreFileServiceImpl
        implements BlobstoreFileService {

	private BlobstoreKeyService keyFactory;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public BlobstoreFileServiceImpl() {
		this.keyFactory = new BlobstoreKeyServiceImpl();
		this.blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	}

	public BlobstoreFileServiceImpl(BlobstoreKeyService keyFactory) {
		this.keyFactory = keyFactory;
		this.blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	}

	public BlobstoreFileServiceImpl(BlobstoreKeyService keyFactory, BlobstoreService blobstoreService) {
		this.keyFactory = keyFactory;
		this.blobstoreService = blobstoreService;
	}

	public BlobstoreKeyService getKeyFactory() {
		return this.keyFactory;
	}

	public void setKeyFactory(BlobstoreKeyService keyFactory) {
		this.keyFactory = keyFactory;
	}

	public BlobstoreService getBlobstoreService() {
		return this.blobstoreService;
	}

	public void setBlobstoreService(BlobstoreService blobstoreService) {
		this.blobstoreService = blobstoreService;
	}

	//MARK: BlobstoreFileService
	@Override
	public void serveStorageBlob(String blobKey,
	                             HttpServletResponse resp) throws IOException {
		BlobKey key = new BlobKey(blobKey);
		this.serveStorageBlob(key, resp);
	}

	@Override
	public void serveStorageBlob(GcsStorableFile file,
	                             HttpServletResponse resp) throws IOException {
		BlobKey key = this.keyFactory.blobKeyForGcsStorableFile(file);
		this.serveStorageBlob(key, resp);
	}

	@Override
	public void serveStorageBlob(BlobKey blobKey,
	                             HttpServletResponse resp) throws IOException {
		try {
			this.blobstoreService.serve(blobKey, resp);
		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	public String toString() {
		return "BlobstoreFileServiceImpl [keyFactory=" + this.keyFactory + ", blobstoreService="
		        + this.blobstoreService + "]";
	}

}
