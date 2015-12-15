package com.dereekb.gae.model.extension.links.components.impl.link;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.exception.UnreleasedDescriptiveLinkException;
import com.dereekb.gae.model.extension.links.components.impl.LinkDataImpl;
import com.dereekb.gae.model.extension.links.components.impl.RelationResultImpl;
import com.dereekb.gae.model.extension.links.descriptor.Descriptor;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescribedModel;
import com.dereekb.gae.model.extension.links.descriptor.impl.DescriptorImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link Link} implementation for a {@link DescribedModel}.
 *
 * Because {@link DescribedModel} may have different linking types, there are
 * different restrictions in place.
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

	// MARK: Link
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
	public RelationResult setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		RelationResult result = null;

		if (this.canActOnModelDescriptor()) {
			result = this.setRelation(change);
		} else {
			String message = "Link must be cleared of its current type before setting a new relation.";
			throw new UnreleasedDescriptiveLinkException(message);
		}

		return result;
	}

	@Override
	public RelationResult addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		RelationResult result = null;
		Descriptor descriptor = this.model.getDescriptor();

		if (descriptor != null) {
			String message = "Link must be cleared of its current value before setting a new relation.";
			throw new UnreleasedDescriptiveLinkException(message);
		} else {
			ModelKey key = change.getRelationKeys().get(0).getModelKey();
			LinkTarget target = this.getLinkTarget();
			String targetType = target.getTargetType();
			String stringId = key.keyAsString();

			descriptor = new DescriptorImpl(targetType, stringId);
			this.model.setDescriptor(descriptor);
			result = RelationResultImpl.hit(key);
		}

		return result;
	}

	@Override
	public RelationResult removeRelation(Relation change) throws RelationChangeException {
		RelationResult result = null;
		Descriptor descriptor = this.model.getDescriptor();

		if (descriptor == null || this.canActOnModelDescriptor(descriptor)) {
			String currentStringId = descriptor.getDescriptorId();

			List<ModelKey> keys = change.getRelationKeys();
			List<String> stringKeys = ModelKey.keysAsStrings(keys);
			Set<String> keysSet = new HashSet<String>(stringKeys);

			if (keysSet.contains(currentStringId)) {
				result = this.clearRelations();
			} else {
				result = RelationResultImpl.redundant(keys);
			}
		} else {
			String message = "Cannot remove from this link. Remove current value from type '"
			        + descriptor.getDescriptorType() + "' first.";
			throw new UnreleasedDescriptiveLinkException(message);
		}

		return result;
	}

	@Override
	public RelationResult clearRelations() {
		RelationResult result = null;
		Descriptor descriptor = this.model.getDescriptor();

		// Can only remove if it is the correct type.
		if (this.canActOnModelDescriptor(descriptor)) {
			this.model.setDescriptor(null);
		} else {
			String message = "Cannot clear from this link. Must clear type '" + descriptor.getDescriptorType()
			        + "' first.";
			throw new UnreleasedDescriptiveLinkException(message);
		}

		return result;
	}

	// MARK: Internal
	private boolean canActOnModelDescriptor() {
		Descriptor descriptor = this.model.getDescriptor();
		return this.canActOnModelDescriptor(descriptor);
	}

	private boolean canActOnModelDescriptor(Descriptor descriptor) {
		LinkTarget target = this.getLinkTarget();
		String targetType = target.getTargetType();
		return this.canActOnType(descriptor, targetType);
	}

	private boolean canActOnType(Descriptor descriptor,
	                             String targetType) {
		return (descriptor != null && descriptor.getDescriptorType().equals(targetType));
	}

}
