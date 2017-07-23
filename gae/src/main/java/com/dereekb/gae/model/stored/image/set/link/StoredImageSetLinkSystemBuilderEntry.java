package com.dereekb.gae.model.stored.image.set.link;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.system.components.SimpleLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.impl.SimpleLinkInfoImpl;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.AbstractMutableLinkSystemBuilderEntry;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.MultipleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.MultipleMutableLinkDataDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkData;
import com.dereekb.gae.model.extension.links.system.mutable.impl.link.SingleMutableLinkDataDelegate;
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.link.StoredImageLinkSystemBuilderEntry;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetLinkSystemBuilderEntry extends AbstractMutableLinkSystemBuilderEntry<StoredImageSet> {

	public static final String LINK_MODEL_TYPE = "StoredImageSet";

	public static final String STORED_IMAGE_SET_ICON_LINK_NAME = "icon";
	public static final String STORED_IMAGE_SET_IMAGES_LINK_NAME = "images";

	private static final ExtendedObjectifyModelKeyUtil<StoredImage> imageUtil = ExtendedObjectifyModelKeyUtil
	        .make(StoredImage.class, ModelKeyType.NUMBER);

	private SimpleLinkInfo iconLinkInfo = new SimpleLinkInfoImpl(STORED_IMAGE_SET_ICON_LINK_NAME, StoredImageLinkSystemBuilderEntry.LINK_MODEL_TYPE);
	private SimpleLinkInfo imagesLinkInfo = new SimpleLinkInfoImpl(STORED_IMAGE_SET_IMAGES_LINK_NAME, StoredImageLinkSystemBuilderEntry.LINK_MODEL_TYPE);

	public StoredImageSetLinkSystemBuilderEntry(ReadService<StoredImageSet> readService) {
		super(readService);
	}
	
	public SimpleLinkInfo getIconLinkInfo() {
		return this.iconLinkInfo;
	}
	
	public void setIconLinkInfo(SimpleLinkInfo iconLinkInfo) {
		if (iconLinkInfo == null) {
			throw new IllegalArgumentException("iconLinkInfo cannot be null.");
		}
	
		this.iconLinkInfo = iconLinkInfo;
	}
	
	public SimpleLinkInfo getImagesLinkInfo() {
		return this.imagesLinkInfo;
	}
	
	public void setImagesLinkInfo(SimpleLinkInfo imagesLinkInfo) {
		if (imagesLinkInfo == null) {
			throw new IllegalArgumentException("imagesLinkInfo cannot be null.");
		}
	
		this.imagesLinkInfo = imagesLinkInfo;
	}

	// MARK: AbstractMutableLinkSystemBuilderEntry
	@Override
	public String getLinkModelType() {
		return LINK_MODEL_TYPE;
	}

	@Override
	public ModelKeyType getModelKeyType() {
		return ModelKeyType.NUMBER;
	}

	@Override
	protected List<MutableLinkData<StoredImageSet>> makeLinkData() {
		List<MutableLinkData<StoredImageSet>> linkData = new ArrayList<MutableLinkData<StoredImageSet>>();

		// Icon
		SingleMutableLinkData<StoredImageSet> iconLinkData = new SingleMutableLinkData<StoredImageSet>(this.iconLinkInfo, new SingleMutableLinkDataDelegate<StoredImageSet>() {

			@Override
			public ModelKey readLinkedModelKey(StoredImageSet model) {
				Key<StoredImage> key = model.getIcon();
				return imageUtil.toModelKey(key);
			}

			@Override
			public void setLinkedModelKey(StoredImageSet model,
			                              ModelKey modelKey) {
				Key<StoredImage> key = imageUtil.fromModelKey(modelKey);
				model.setIcon(key);
			}
			
		});
		
		linkData.add(iconLinkData);
		
		// Images
		MultipleMutableLinkData<StoredImageSet> imagesLinkData = new MultipleMutableLinkData<StoredImageSet>(this.imagesLinkInfo, new MultipleMutableLinkDataDelegate<StoredImageSet>() {

			@Override
			public Set<ModelKey> readLinkedModelKeys(StoredImageSet model) {
				return imageUtil.setFromKeys(model.getImages());
			}

			@Override
			public void setLinkedModelKeys(StoredImageSet model,
			                               Set<ModelKey> keys) {
				model.setImages(imageUtil.convertFrom(keys));
			}
			
		});
		
		linkData.add(imagesLinkData);
		
		return linkData;
	}

	@Override
	public String toString() {
		return "StoredImageSetLinkSystemBuilderEntry [iconLinkInfo=" + this.iconLinkInfo + ", imagesLinkInfo="
		        + this.imagesLinkInfo + "]";
	}

}
