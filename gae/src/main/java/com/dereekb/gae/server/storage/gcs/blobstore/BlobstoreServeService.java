package com.dereekb.gae.server.storage.gcs.blobstore;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.dereekb.gae.server.storage.gcs.GcsStorageFileRequest;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

/**
 * Helper class for using the blobstore service to serve blobs.
 *
 * @author dereekb
 */
public final class BlobstoreServeService {

	private BlobstoreKeyFactory keyFactory;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public BlobstoreServeService() {
		this(new BlobstoreKeyFactory());
	}

	public BlobstoreServeService(BlobstoreService blobstoreService) {
		this(new BlobstoreKeyFactory(blobstoreService), blobstoreService);
	}

	public BlobstoreServeService(BlobstoreKeyFactory keyFactory) {
		super();
		this.setKeyFactory(keyFactory);
		this.blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	}

	public BlobstoreServeService(BlobstoreKeyFactory keyFactory, BlobstoreService blobstoreService) {
		super();
		this.setKeyFactory(keyFactory);
		this.blobstoreService = blobstoreService;
	}

	public void serveStorageBlob(String keyString,
	                             HttpServletResponse resp) throws IOException {
		BlobKey key = new BlobKey(keyString);
		this.serveStorageBlob(key, resp);
	}

	public void serveStorageBlob(GcsStorageFileRequest request,
	                             HttpServletResponse resp) throws IOException {
		BlobKey key = this.keyFactory.blobKeyForStorageRequest(request);
		this.serveStorageBlob(key, resp);
	}

	public void serveStorageBlob(BlobKey key,
	                             HttpServletResponse resp) throws IOException {
		try {
			this.blobstoreService.serve(key, resp);
		} catch (IOException e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_NO_CONTENT);
		}
	}

	public BlobstoreKeyFactory getKeyFactory() {
		return this.keyFactory;
	}

	public void setKeyFactory(BlobstoreKeyFactory keyFactory) throws NullPointerException {
		if (keyFactory == null) {
			throw new NullPointerException();
		}

		this.keyFactory = keyFactory;
	}

	public BlobstoreService getBlobstoreService() {
		return this.blobstoreService;
	}

	public void setBlobstoreService(BlobstoreService blobstoreService) {
		this.blobstoreService = blobstoreService;
	}
}
