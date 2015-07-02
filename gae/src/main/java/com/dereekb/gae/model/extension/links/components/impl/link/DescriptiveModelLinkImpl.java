package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.DescriptivelyLinkedModel;
import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.UnreleasedDescriptiveLinkException;
import com.dereekb.gae.model.extension.links.components.impl.LinkDataImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} implementation for a {@link DescriptivelyLinkedModel}.
 *
 * Because {@link DescriptivelyLinkedModel} may have different linking types,
 * there are different restrictions in place.
 *
 * For once, a relation can only be changed to a similar info type, or must be
 * released/removed before changing the info type.
 *
 * @author dereekb
 */
public class DescriptiveModelLinkImpl
        implements Link {

	private LinkInfo linkInfo;
	private DescriptivelyLinkedModel model;

	public DescriptiveModelLinkImpl(LinkInfo linkInfo, DescriptivelyLinkedModel model) {
		this.model = model;
		this.linkInfo = linkInfo;
	}

	public DescriptivelyLinkedModel getModel() {
		return this.model;
	}

	public void setModel(DescriptivelyLinkedModel model) {
		this.model = model;
	}

	public LinkInfo getLinkInfo() {
		return this.linkInfo;
	}

	public void setLinkInfo(LinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	// Link
	@Override
	public String getLinkName() {
		return this.linkInfo.getLinkName();
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.linkInfo.getLinkModelKey();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.linkInfo.getLinkTarget();
	}

	@Override
	public void addRelation(Relation change) throws UnreleasedDescriptiveLinkException {
		LinkTarget target = this.getLinkTarget();
		String targetType = target.getTargetType();
		String currentType = this.model.getInfoType();

		if (currentType == null || currentType.equals(targetType)) {
			ModelKey key = change.getRelationKeys().get(0).getModelKey();
			String stringId = key.keyAsString();

			this.model.setInfoType(targetType);
			this.model.setInfoIdentifier(stringId);
		} else {
			throw new UnreleasedDescriptiveLinkException();
		}
	}

	@Override
	public void removeRelation(Relation change) {
		String currentStringId = this.model.getInfoIdentifier();

		if (currentStringId != null) {
			List<ModelKey> keys = change.getRelationKeys();
			List<String> stringKeys = ModelKey.keysAsStrings(keys);
			Set<String> keysSet = new HashSet<String>(stringKeys);

			if (keysSet.contains(currentStringId)) {
				this.clearRelations();
			}
		}
	}

	@Override
	public LinkData getLinkData() {
		String currentStringId = this.model.getInfoIdentifier();
		ModelKey key = ModelKey.convert(currentStringId);
		LinkDataImpl data = new LinkDataImpl(this.linkInfo, key);
		return data;
	}

	@Override
	public void clearRelations() {
		this.model.setInfoType(null);
		this.model.setInfoIdentifier(null);
	}

}
