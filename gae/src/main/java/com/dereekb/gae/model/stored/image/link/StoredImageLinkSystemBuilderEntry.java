package com.dereekb.gae.model.stored.image.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.impl.SimpleLinkInfoImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkDataDelegate;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.link.StoredBlobLinkSystemBuilderEntry;
import com.dereekb.gae.model.stored.image.StoredImage;
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
public class StoredImageLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<StoredImage> {

	public static final String STORED_IMAGE_LINK_TYPE = "StoredImage";

	private static final ExtendedObjectifyModelKeyUtil<StoredBlob> blobUtil = ExtendedObjectifyModelKeyUtil
	        .make(StoredBlob.class, ModelKeyType.NUMBER);
	
	private SimpleLinkInfo blobLinkInfo = new SimpleLinkInfoImpl(StoredBlobLinkSystemBuilderEntry.STORED_BLOB_LINK_TYPE);

	public StoredImageLinkSystemBuilderEntry(ReadService<StoredImage> readService) {
		super(readService);
	}
	
	public SimpleLinkInfo getBlobLinkInfo() {
		return this.blobLinkInfo;
	}
	
	public void setBlobLinkInfo(SimpleLinkInfo blobLinkInfo) {
		if (blobLinkInfo == null) {
			throw new IllegalArgumentException("blobLinkInfo cannot be null.");
		}
	
		this.blobLinkInfo = blobLinkInfo;
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	public String getLinkModelType() {
		return STORED_IMAGE_LINK_TYPE;
	}

	@Override
	protected List<MutableLinkData<StoredImage>> makeLinkData() {
		List<MutableLinkData<StoredImage>> linkData = new ArrayList<MutableLinkData<StoredImage>>();

		// Blob Link
		SingleMutableLinkData<StoredImage> blobLinkData = new SingleMutableLinkData<StoredImage>(this.blobLinkInfo, new SingleMutableLinkDataDelegate<StoredImage>() {

			@Override
			public ModelKey readLinkedModelKey(StoredImage model) {
				Key<StoredBlob> key = model.getStoredBlob();
				return blobUtil.toModelKey(key);
			}

			@Override
			public void setLinkedModelKey(StoredImage model,
			                              ModelKey modelKey) {
				Key<StoredBlob> key = blobUtil.fromModelKey(modelKey);
				model.setStoredBlob(key);
			}
			
		});
		
		linkData.add(blobLinkData);
		
		return linkData;
	}

	@Override
	public String toString() {
		return "StoredImageLinkSystemBuilderEntry [blobLinkInfo=" + this.blobLinkInfo + "]";
	}

}
