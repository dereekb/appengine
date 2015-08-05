package com.dereekb.gae.model.stored.image.link;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.impl.LinkInfoImpl;
import com.dereekb.gae.model.extension.links.components.impl.LinkTargetImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkCollectionImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.LinkImpl;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLink;
import com.dereekb.gae.model.extension.links.components.impl.link.SingleLinkDelegate;
import com.dereekb.gae.model.extension.links.components.system.impl.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.impl.AbstractModelLinkSystemEntry;
import com.dereekb.gae.model.geo.place.GeoPlace;
import com.dereekb.gae.model.geo.place.link.GeoPlaceLinkSystemEntry;
import com.dereekb.gae.model.stored.blob.StoredBlob;
import com.dereekb.gae.model.stored.blob.link.StoredBlobLinkSystemEntry;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.model.stored.image.set.link.StoredImageSetLinkSystemEntry;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link StoredImage}.
 *
 * @author dereekb
 *
 */
public class StoredImageLinkSystemEntry extends AbstractModelLinkSystemEntry<StoredImage> {

	public static final String STORED_IMAGE_LINK_TYPE = "StoredImage";

	private static final ExtendedObjectifyModelKeyUtil<GeoPlace> geoPlaceUtil = ExtendedObjectifyModelKeyUtil.make(
	        GeoPlace.class, ModelKeyType.NUMBER);
	private static final ExtendedObjectifyModelKeyUtil<StoredBlob> blobUtil = ExtendedObjectifyModelKeyUtil.make(
	        StoredBlob.class, ModelKeyType.NUMBER);
	private static final ExtendedObjectifyModelKeyUtil<StoredImageSet> imageSetUtil = ExtendedObjectifyModelKeyUtil
	        .make(StoredImageSet.class, ModelKeyType.NUMBER);

	private String blobLinkName = "Blob";
	private String geoPlaceLinkName = "GeoPlace";
	private String imageSetLinkName = "Images";

	private LinkTarget blobTarget = new LinkTargetImpl(StoredBlobLinkSystemEntry.STORED_BLOB_LINK_TYPE,
	        ModelKeyType.NUMBER);
	private LinkTarget geoPlaceTarget = new LinkTargetImpl(GeoPlaceLinkSystemEntry.GEO_PLACE_LINK_TYPE,
	        ModelKeyType.NUMBER);
	private LinkTarget imageSetTarget = new LinkTargetImpl(StoredImageSetLinkSystemEntry.STORED_IMAGE_SET_LINK_TYPE,
	        ModelKeyType.NUMBER);

	public StoredImageLinkSystemEntry(ReadService<StoredImage> service, ConfiguredSetter<StoredImage> setter) {
		super(STORED_IMAGE_LINK_TYPE, service, setter);
	}

	public String getStoredBlobLinkName() {
		return this.blobLinkName;
	}

	public void setStoredBlobLinkName(String storedBlobLinkName) {
		this.blobLinkName = storedBlobLinkName;
	}

	public String getGeoPlaceLinkName() {
		return this.geoPlaceLinkName;
	}

	public void setGeoPlaceLinkName(String geoPlaceLinkName) {
		this.geoPlaceLinkName = geoPlaceLinkName;
	}

	public String getImageSetLinkName() {
		return this.imageSetLinkName;
	}

	public void setImageSetLinkName(String imageSetLinkName) {
		this.imageSetLinkName = imageSetLinkName;
	}

	public LinkTarget getStoredBlobTarget() {
		return this.blobTarget;
	}

	public void setStoredBlobTarget(LinkTarget storedBlobTarget) {
		this.blobTarget = storedBlobTarget;
	}

	public LinkTarget getGeoPlaceTarget() {
		return this.geoPlaceTarget;
	}

	public void setGeoPlaceTarget(LinkTarget geoPlaceTarget) {
		this.geoPlaceTarget = geoPlaceTarget;
	}

	public LinkTarget getImageSetTarget() {
		return this.imageSetTarget;
	}

	public void setImageSetTarget(LinkTarget imageSetTarget) {
		this.imageSetTarget = imageSetTarget;
	}

	@Override
	public List<Link> makeLinksForModel(final StoredImage model) {
		List<Link> links = new ArrayList<Link>();

		ModelKey key = model.getModelKey();

		// Blob Link
		LinkInfoImpl blobLinkInfo = new LinkInfoImpl(this.blobLinkName, key, this.blobTarget);
		LinkImpl blobLink = new LinkImpl(blobLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				Key<StoredBlob> key = model.getBlob();
				return blobUtil.toModelKey(key);
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<StoredBlob> key = blobUtil.fromModelKey(modelKey);
				model.setBlob(key);
			}

		}));

		links.add(blobLink);

		// GeoPlace Link
		LinkInfoImpl geoPlaceLinkInfo = new LinkInfoImpl(this.geoPlaceLinkName, key, this.geoPlaceTarget);
		LinkImpl iconLink = new LinkImpl(geoPlaceLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				Key<GeoPlace> key = model.getGeoPlace();
				return geoPlaceUtil.toModelKey(key);
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<GeoPlace> key = geoPlaceUtil.fromModelKey(modelKey);
				model.setGeoPlace(key);
			}

		}));

		links.add(iconLink);

		// ImageSet Links
		LinkInfoImpl imageSetLinkInfo = new LinkInfoImpl(this.imageSetLinkName, key, this.imageSetTarget);
		LinkCollectionImpl<Key<StoredImageSet>> imageSetLink = new LinkCollectionImpl<Key<StoredImageSet>>(
		        imageSetLinkInfo, model.getImageSets(), imageSetUtil);

		links.add(imageSetLink);

		return links;
	}

	@Override
	public String toString() {
		return "StoredImageLinkSystemEntry [blobLinkName=" + this.blobLinkName + ", geoPlaceLinkName="
		        + this.geoPlaceLinkName + ", imageSetLinkName=" + this.imageSetLinkName + ", blobTarget="
		        + this.blobTarget + ", geoPlaceTarget=" + this.geoPlaceTarget + ", imageSetTarget="
		        + this.imageSetTarget + ", modelType=" + this.modelType + ", service=" + this.service + ", setter="
		        + this.setter + ", reviewer=" + this.reviewer + ", validator=" + this.validator + ", reverseLinkNames="
		        + this.reverseLinkNames + "]";
	}

}
