package com.dereekb.gae.model.stored.image.set.link;

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
import com.dereekb.gae.model.stored.image.StoredImage;
import com.dereekb.gae.model.stored.image.link.StoredImageLinkSystemEntry;
import com.dereekb.gae.model.stored.image.set.StoredImageSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.keys.util.ExtendedObjectifyModelKeyUtil;
import com.dereekb.gae.server.datastore.utility.ConfiguredSetter;
import com.googlecode.objectify.Key;

/**
 * {@link LinkSystemEntry} implementation for {@link StoredImageSet}.
 *
 * @author dereekb
 *
 */
public class StoredImageSetLinkSystemEntry extends AbstractModelLinkSystemEntry<StoredImageSet> {

	public static final String STORED_IMAGE_SET_LINK_TYPE = "StoredImageSet";
	private static final ExtendedObjectifyModelKeyUtil<StoredImage> imageUtil = ExtendedObjectifyModelKeyUtil.make(
	        StoredImage.class, ModelKeyType.NUMBER);

	private String iconLinkName = "Icon";
	private String imagesLinkName = "Images";

	private LinkTarget imagesTarget = new LinkTargetImpl(StoredImageLinkSystemEntry.STORED_IMAGE_LINK_TYPE,
	        ModelKeyType.NUMBER);

	public StoredImageSetLinkSystemEntry(ReadService<StoredImageSet> service, ConfiguredSetter<StoredImageSet> setter) {
		super(STORED_IMAGE_SET_LINK_TYPE, service, setter);
	}

	public String getIconLinkName() {
		return this.iconLinkName;
	}

	public void setIconLinkName(String iconLinkName) {
		this.iconLinkName = iconLinkName;
	}

	public String getImagesLinkName() {
		return this.imagesLinkName;
	}

	public void setImagesLinkName(String imagesLinkName) {
		this.imagesLinkName = imagesLinkName;
	}

	public LinkTarget getIconTarget() {
		return this.imagesTarget;
	}

	public void setIconTarget(LinkTarget imagesTarget) {
		this.imagesTarget = imagesTarget;
	}

	public LinkTarget getImagesTarget() {
		return this.imagesTarget;
	}

	public void setImagesTarget(LinkTarget imagesTarget) {
		this.imagesTarget = imagesTarget;
	}

	@Override
	public List<Link> makeLinksForModel(final StoredImageSet model) {
		List<Link> links = new ArrayList<Link>();

		ModelKey key = model.getModelKey();

		// Icon Link
		LinkInfoImpl iconLinkInfo = new LinkInfoImpl(this.iconLinkName, key, this.imagesTarget);
		LinkImpl iconLink = new LinkImpl(iconLinkInfo, new SingleLink(new SingleLinkDelegate() {

			@Override
			public ModelKey getKey() {
				Key<StoredImage> key = model.getIcon();
				return imageUtil.toModelKey(key);
			}

			@Override
			public void setKey(ModelKey modelKey) {
				Key<StoredImage> key = imageUtil.fromModelKey(modelKey);
				model.setIcon(key);
			}

		}));

		links.add(iconLink);

		// Images Link
		LinkInfoImpl imagesLinkInfo = new LinkInfoImpl(this.imagesLinkName, key, this.imagesTarget);
		LinkCollectionImpl<Key<StoredImage>> imagesLink = new LinkCollectionImpl<Key<StoredImage>>(imagesLinkInfo,
		        model.getImages(), imageUtil);

		links.add(imagesLink);

		return links;
	}

	@Override
	public String toString() {
		return "StoredImageSetLinkSystemEntry [iconLinkName=" + this.iconLinkName + ", imagesLinkName="
		        + this.imagesLinkName + ", imagesTarget=" + this.imagesTarget + ", modelType=" + this.modelType
		        + ", indexService=" + this.service + ", setter=" + this.setter + ", reviewer=" + this.reviewer
		        + ", validator=" + this.validator + ", reverseLinkNames=" + this.reverseLinkNames + "]";
	}

}
