package com.dereekb.gae.server.storage.services.gcs.impl;

import com.dereekb.gae.server.deprecated.storage.object.file.StorableContent;
import com.dereekb.gae.server.deprecated.storage.services.gcs.GcsStorageSystem;
import com.dereekb.gae.server.deprecated.storage.services.gcs.object.request.GcsStorableFileSaveRequest;
import com.dereekb.gae.server.deprecated.storage.services.gcs.object.request.GcsStorableFileSaveRequestBuilder;
import com.google.appengine.tools.cloudstorage.GcsFileOptions;

/**
 * Save Request used by {@link GcsStorageSystem}. Wraps {@link StorableContent}.
 * <p>
 * {@link GcsStorableFileSaveRequest} can be safely generated from a
 * {@link GcsStorableFileSaveRequestBuilder}. If you choose to build these
 * manually, ensure that the {@link #gcsFileOptions} reflect the
 * {@link StorableContent}.
 * </p>
 *
 * @author dereekb
 */
public class GcsStorableFileSaveRequestImpl extends GcsStorableFileRequestImpl
        implements GcsStorableFileSaveRequest {

	private StorableContent content;
	private GcsFileOptions gcsFileOptions;

	public GcsStorableFileSaveRequestImpl(String gcsBucket, StorableContent content, GcsFileOptions gcsFileOptions) {
		super(gcsBucket, content);
		this.setContent(content);
		this.setGcsFileOptions(gcsFileOptions);
	}

	public StorableContent getContent() {
		return this.content;
	}

	public void setContent(StorableContent content) {
		super.setFile(content);
		this.content = content;
	}

	@Override
	public GcsFileOptions getGcsFileOptions() {
		return this.gcsFileOptions;
	}

	public void setGcsFileOptions(GcsFileOptions gcsFileOptions) {

		if (gcsFileOptions == null) {
			GcsFileOptions.Builder builder = new GcsFileOptions.Builder();
			gcsFileOptions = builder.mimeType(this.content.getContentType()).build();
		}

		this.gcsFileOptions = gcsFileOptions;
	}

	// MARK: GcsStorableFileSaveRequest
	@Override
	public byte[] getFileData() {
		return this.content.getFileData();
	}

	@Override
    public String toString() {
		return "GcsStorableFileSaveRequestImpl [content=" + this.content + ", gcsFileOptions=" + this.gcsFileOptions
		        + ", getGcsBucket()=" + this.getGcsBucket() + "]";
    }

}
