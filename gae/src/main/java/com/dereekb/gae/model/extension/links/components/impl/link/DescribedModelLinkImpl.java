package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.UnreleasedDescriptiveLinkException;
import com.dereekb.gae.model.extension.links.components.impl.LinkDataImpl;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.DescriptorImpl;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} implementation for a {@link DescribedModel}.
 *
 * Because {@link DescribedModel} may have different linking types,
 * there are different restrictions in place.
 *
 * For once, a relation can only be changed to a similar info type, or must be
 * released/removed before changing the info type.
 *
 * @author dereekb
 */
public class DescribedModelLinkImpl
        implements Link {

	private LinkInfo linkInfo;
	private DescribedModel model;

	public DescribedModelLinkImpl(LinkInfo linkInfo, DescribedModel model) {
		this.model = model;
		this.linkInfo = linkInfo;
	}

	public DescribedModel getModel() {
		return this.model;
	}

	public void setModel(DescribedModel model) {
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

		Descriptor descriptor = this.model.getDescriptor();

		if (descriptor != null) {
			String currentType = descriptor.getDescriptorType();

			if (currentType == null || currentType.equals(targetType)) {
				ModelKey key = change.getRelationKeys().get(0).getModelKey();
				String stringId = key.keyAsString();

				descriptor = new DescriptorImpl(targetType, stringId);
				this.model.setDescriptor(descriptor);
			}
		} else {
			throw new UnreleasedDescriptiveLinkException();
		}
	}

	@Override
	public void removeRelation(Relation change) {
		Descriptor descriptor = this.model.getDescriptor();

		if (descriptor != null) {
			String currentStringId = descriptor.getDescriptorId();

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
		Descriptor descriptor = this.model.getDescriptor();
		ModelKey key = null;

		if (descriptor != null) {
			/*
			 * TODO: This conversion seems dangerous. Perhaps add a delegate or
			 * something to make sure the key is converted properly depending on
			 * the descriptor's type...
			 */

			String infoId = descriptor.getDescriptorId();
			key = ModelKey.convert(infoId);
		}

		LinkDataImpl data = new LinkDataImpl(this.linkInfo, key);
		return data;
	}

	@Override
	public void clearRelations() {
		this.model.setDescriptor(null);
	}

}
