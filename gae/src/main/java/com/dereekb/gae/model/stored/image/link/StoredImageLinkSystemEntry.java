package com.dereekb.gae.model.stored.image.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.CrudService;
import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.LinkTargetImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.ReadOnlyLinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLink;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLinkDelegate;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractModelLinkSystemEntry;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.link.StoredBlobLinkSystemEntry;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link StoredImage}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class StoredImageLinkSystemEntry extends AbstractModelLinkSystemEntry<StoredImage> {

	public static final String STORED_IMAGE_LINK_TYPE = "StoredImage";

	private static final ExtendedObjectifyModelKeyUtil<StoredBlob> blobUtil = ExtendedObjectifyModelKeyUtil
	        .make(StoredBlob.class, ModelKeyType.NUMBER);

	private String blobLinkName = StoredBlobLinkSystemEntry.STORED_BLOB_LINK_TYPE;

	private LinkTarget blobTarget = new LinkTargetImpl(StoredBlobLinkSystemEntry.STORED_BLOB_LINK_TYPE,
	        ModelKeyType.NUMBER);

	public StoredImageLinkSystemEntry(CrudService<StoredImage> crudService, Updater<StoredImage> updater) {
		super(STORED_IMAGE_LINK_TYPE, crudService, crudService, updater);
	}

	public StoredImageLinkSystemEntry(ReadService<StoredImage> readService,
	        DeleteService<StoredImage> deleteService,
	        Updater<StoredImage> updater) {
		super(STORED_IMAGE_LINK_TYPE, readService, deleteService, updater);
	}

	public String getStoredBlobLinkName() {
		return this.blobLinkName;
	}

	public void setStoredBlobLinkName(String storedBlobLinkName) {
		this.blobLinkName = storedBlobLinkName;
	}

	public LinkTarget getStoredBlobTarget() {
		return this.blobTarget;
	}

	public void setStoredBlobTarget(LinkTarget storedBlobTarget) {
		this.blobTarget = storedBlobTarget;
	}

	@Override
	public List<Link> getLinks(final StoredImage model) {
		List<Link> links = new ArrayList<Link>();

		ModelKey key = model.getModelKey();

		// Blob Link
		LinkInfoImpl blobLinkInfo = new LinkInfoImpl(this.blobLinkName, key, this.blobTarget);
		LinkImpl blobLink = new ReadOnlyLinkImpl(blobLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				Key<StoredBlob> key = model.getStoredBlob();
				return blobUtil.toModelKey(key);
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<StoredBlob> key = blobUtil.fromModelKey(modelKey);
				model.setStoredBlob(key);
			}

		}));

		links.add(blobLink);

		return links;
	}

	@Override
	public String toString() {
		return "StoredImageLinkSystemEntry [blobLinkName=" + this.blobLinkName + ", blobTarget=" + this.blobTarget
		        + ", modelType=" + this.modelType + ", indexService=" + this.readService + ", updater=" + this.updater
		        + ", reviewer=" + this.reviewer + ", validator=" + this.validator + ", reverseLinkNames="
		        + this.getReverseLinkNames() + "]";
	}

}
