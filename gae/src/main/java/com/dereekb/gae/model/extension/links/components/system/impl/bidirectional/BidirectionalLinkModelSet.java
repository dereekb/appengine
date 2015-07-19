package com.dereekb.gae.model.extension.links.components.system.impl.bidirectional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.LinkInfo;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.exception.LinkSaveConditionException;
import com.dereekb.gae.model.extension.links.components.exception.LinkSaveException;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.model.change.LinkModelSetChange;
import com.dereekb.gae.model.extension.links.components.system.exception.UnrelatedLinkException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkModelSet} implementation for {@link BidirectionalLinkSystem}.
 *
 * @author dereekb
 *
 */
public final class BidirectionalLinkModelSet
        implements LinkModelSet, BidirectionalLinkModelDelegate {

	private final String setModelType;
	private final BidirectionalLinkModelSetDelegate delegate;
	private final LinkModelSet primarySet;

	/**
	 * Set of sets that have already been requested.
	 */
	private Set<String> loadedSecondarySets = new HashSet<String>();

	/**
	 * All loaded secondary sets.
	 */
	private Map<String, LinkModelSet> secondarySets = new HashMap<String, LinkModelSet>();

	public BidirectionalLinkModelSet(String setModelType,
	        BidirectionalLinkModelSetDelegate delegate,
	        LinkModelSet primarySet) {

		if (delegate == null) {
			throw new IllegalArgumentException("Delegate cannot be null.");
		}

		if (primarySet == null) {
			throw new IllegalArgumentException("Primary set cannot be null.");
		}

		this.setModelType = setModelType;
		this.delegate = delegate;
		this.primarySet = primarySet;
	}

	public Set<String> getLoadedSecondarySets() {
		return this.loadedSecondarySets;
	}

	public void setLoadedSecondarySets(Set<String> loadedSecondarySets) {
		this.loadedSecondarySets = loadedSecondarySets;
	}

	public Map<String, LinkModelSet> getSecondarySets() {
		return this.secondarySets;
	}

	public void setSecondarySets(Map<String, LinkModelSet> secondarySets) {
		this.secondarySets = secondarySets;
	}

	public String getSetType() {
		return this.setModelType;
	}

	public BidirectionalLinkModelSetDelegate getDelegate() {
		return this.delegate;
	}

	public LinkModelSet getPrimarySet() {
		return this.primarySet;
	}

	// LinkModelSet
	@Override
	public Set<ModelKey> getModelKeys() {
		return this.primarySet.getModelKeys();
	}

	@Override
	public Collection<LinkModel> getLinkModels() {
		Collection<LinkModel> models = this.primarySet.getLinkModels();
		return this.wrapModels(models);
	}

	@Override
	public List<LinkModel> getModelsForKeys(Collection<ModelKey> keys) {
		Collection<LinkModel> models = this.primarySet.getModelsForKeys(keys);
		return this.wrapModels(models);
	}

	@Override
	public LinkModel getModelForKey(ModelKey key) {
		LinkModel model = this.primarySet.getModelForKey(key);
		return this.safeWrapModel(model);
	}

	@Override
	public Set<ModelKey> getAvailableModelKeys() {
		return this.primarySet.getAvailableModelKeys();
	}

	@Override
	public Set<ModelKey> getMissingModelKeys() {
		return this.primarySet.getMissingModelKeys();
	}

	@Override
	public void loadModels(Collection<ModelKey> keys) {
		this.primarySet.loadModels(keys);
	}

	@Override
	public LinkModelSetChange getChanges() {
		return this.primarySet.getChanges();
	}

	@Override
	public void validateChanges() throws LinkSaveConditionException {
		this.primarySet.validateChanges();

		for (LinkModelSet set : this.secondarySets.values()) {
			set.validateChanges();
		}
	}

	@Override
	public void save(boolean validate) throws LinkSaveException, LinkSaveConditionException {
		if (validate) {
			this.validateChanges();
		}

		try {
			this.primarySet.save(false);

			for (LinkModelSet set : this.secondarySets.values()) {
				set.save(false);
			}
		} catch (LinkSaveException e) {
			throw e; // Forward the exception.
		} catch (RuntimeException e) {
			throw new LinkSaveException(e);
		}
	}

	// BidirectionalLinkModelDelegate
	@Override
	public List<LinkModel> getReverseLinkModels(LinkInfo info,
	                                            List<ModelKey> keys) {
		List<LinkModel> linkModels;
		LinkModelSet set = this.loadSet(info);

		if (set != null) {
			set.loadModels(keys);
			linkModels = set.getModelsForKeys(keys);
		} else {
			linkModels = Collections.emptyList();
		}

		return linkModels;
	}

	@Override
	public String getReverseLinkName(LinkInfo info) {
		return this.delegate.getReverseLinkName(this.setModelType, info);
	}

	// Internal
	private LinkModelSet loadSet(LinkInfo info) {
		LinkModelSet set = null;
		LinkTarget target = info.getLinkTarget();
		String targetType = target.getTargetType();

		if (this.loadedSecondarySets.contains(targetType) == false) {
			this.loadedSecondarySets.add(targetType);

			try {
				set = this.delegate.loadTargetTypeSet(this.setModelType, info);
				this.secondarySets.put(targetType, set);
			} catch (UnrelatedLinkException e) {
			}
		} else {
			set = this.secondarySets.get(targetType);
		}

		return set;
	}

	private List<LinkModel> wrapModels(Collection<LinkModel> models) {
		List<LinkModel> wrappedModels = new ArrayList<LinkModel>();

		for (LinkModel model : models) {
			LinkModel wrappedModel = this.wrapModel(model);
			wrappedModels.add(wrappedModel);
		}

		return wrappedModels;
	}

	private LinkModel safeWrapModel(LinkModel model) {
		if (model != null) {
			return this.wrapModel(model);
		} else {
			return null;
		}
	}

	private LinkModel wrapModel(LinkModel model) {
		return new BidirectionalLinkModel(this, model);
	}

	@Override
	public String toString() {
		return "BidirectionalLinkModelSet [setModelType=" + this.setModelType + ", primarySet=" + this.primarySet
		        + ", loadedSecondarySets=" + this.loadedSecondarySets + "]";
	}

}
