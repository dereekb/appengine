package com.dereekb.gae.model.extension.links.deleter.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterChangeType;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterService;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterServiceEntry;
import com.dereekb.gae.model.extension.links.deleter.LinkDeleterServiceRequest;
import com.dereekb.gae.model.extension.links.deleter.exception.UnregisteredDeleterException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link LinkDeleterService} implementation.
 *
 * @author dereekb
 *
 */
public class LinkDeleterServiceImpl
        implements LinkDeleterService {

	private LinkSystem linkSystem;
	private Map<String, LinkDeleterServiceEntry> entries;

	private boolean validateSetChanges = false;
	private LinkDeleterChangeType defaultChangeType = LinkDeleterChangeType.UNLINK;

	public LinkDeleterServiceImpl(LinkSystem linkSystem, Collection<LinkDeleterServiceEntry> entries) {
		this.setLinkSystem(linkSystem);
		this.setEntries(entries);
	}

	public LinkDeleterServiceImpl(LinkSystem linkSystem, Map<String, LinkDeleterServiceEntry> entries) {
		this.setLinkSystem(linkSystem);
		this.setEntries(entries);
	}

	public LinkSystem getLinkSystem() {
		return this.linkSystem;
	}

	public void setLinkSystem(LinkSystem linkSystem) {
		this.linkSystem = linkSystem;
	}

	public Map<String, LinkDeleterServiceEntry> getEntries() {
		return this.entries;
	}

	private void setEntries(Collection<LinkDeleterServiceEntry> entries) {
		Map<String, LinkDeleterServiceEntry> map = new HashMap<String, LinkDeleterServiceEntry>();

		for (LinkDeleterServiceEntry entry : entries) {
			map.put(entry.getModelType(), entry);
		}

		this.setEntries(map);
	}

	public void setEntries(Map<String, LinkDeleterServiceEntry> entries) {
		this.entries = entries;
	}

	public boolean isValidateSetChanges() {
		return this.validateSetChanges;
	}

	public void setValidateSetChanges(boolean validateSetChanges) {
		this.validateSetChanges = validateSetChanges;
	}

	public LinkDeleterChangeType getDefaultChangeType() {
		return this.defaultChangeType;
	}


    public void setDefaultChangeType(LinkDeleterChangeType defaultChangeType) {
		this.defaultChangeType = defaultChangeType;
	}

	// MARK: LinkDeleterService
	@Override
	public void deleteLinks(LinkDeleterServiceRequest request) throws UnregisteredDeleterException {

		Instance instance = new Instance(request);
		instance.deleteLinks();
	}

	private LinkDeleterServiceEntry getEntryForModelType(String modelType) throws UnregisteredDeleterException {
		LinkDeleterServiceEntry entry = this.entries.get(modelType);

		if (entry == null) {
			throw new UnregisteredDeleterException(modelType);
		}

		return entry;
	}

	private class Instance {

		private final LinkModelSet set;
		private final LinkDeleterServiceEntry entry;

		private final Map<String, LinkDeleterChangeType> changeMap;
		private final Map<String, Set<ModelKey>> toDelete;

		public Instance(LinkDeleterServiceRequest request) {
			String modelType = request.getModelType();
			Collection<ModelKey> keys = request.getTargetKeys();

			this.entry = LinkDeleterServiceImpl.this.getEntryForModelType(modelType);
			this.set = LinkDeleterServiceImpl.this.linkSystem.loadSet(modelType, keys);
			this.changeMap = this.entry.getDeleteChangesMap();
			this.toDelete = new HashMap<String, Set<ModelKey>>();
        }

		public void deleteLinks() {
			Collection<LinkModel> models = this.set.getLinkModels();

			for (LinkModel model : models) {
	            this.deleteLinksForModel(model);
            }

			this.set.save(LinkDeleterServiceImpl.this.validateSetChanges);
			this.performDeletes();
		}

		private void deleteLinksForModel(LinkModel model) {
			ModelInstance instance = new ModelInstance(model);
			instance.deleteLinks();
		}

		private void queueForDelete(String modelType,
		                            Collection<ModelKey> keys) {
			Set<ModelKey> set = this.getDeleteSetForType(modelType);
			set.addAll(keys);
		}

		private Set<ModelKey> getDeleteSetForType(String modelType) {
			Set<ModelKey> set = this.toDelete.get(modelType);

			if (set == null) {
				set = new HashSet<ModelKey>();
				this.toDelete.put(modelType, set);
			}

			return set;
		}

		private void performDeletes() {
			Set<String> modelTypes = this.toDelete.keySet();

			for (String modelType : modelTypes) {
				LinkDeleterServiceEntry entry = LinkDeleterServiceImpl.this.getEntryForModelType(modelType);
				Set<ModelKey> keys = this.toDelete.get(modelType);
				entry.deleteModels(keys);
			}

        }

		private class ModelInstance {

			private final LinkModel model;

			public ModelInstance(LinkModel model) {
				this.model = model;
			}

			public void deleteLinks() {
				Collection<Link> links = this.model.getLinks();

				for (Link link : links) {
					this.deleteLink(link);
				}
			}

			private void deleteLink(Link link) {
				String name = link.getLinkName();
				LinkDeleterChangeType changeType = Instance.this.changeMap.get(name);

				if (changeType == null) {
					changeType = LinkDeleterServiceImpl.this.defaultChangeType;
				}

				switch (changeType) {
					case DELETE:
						LinkData linkData = link.getLinkData();
						LinkTarget linkTarget = linkData.getLinkTarget();

						String targetType = linkTarget.getTargetType();
						List<ModelKey> targetKeys = linkData.getRelationKeys();

						Instance.this.queueForDelete(targetType, targetKeys);
						// Fall through and clear those relations too.
					case UNLINK:
						link.clearRelations();
						break;
					case NONE:
						break;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "LinkDeleterServiceImpl [linkSystem=" + this.linkSystem + ", entries=" + this.entries
		        + ", validateSetChanges=" + this.validateSetChanges + ", defaultChangeType=" + this.defaultChangeType
		        + "]";
	}

}
