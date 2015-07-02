package com.dereekb.gae.model.extension.links.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.impl.RelationImpl;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.LinkModelSet;
import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.service.LinkChange;
import com.dereekb.gae.model.extension.links.service.LinkChangeAction;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * Utility class for processing {@link LinkChange} changes.
 *
 * @author dereekb
 *
 */
public class LinkChangesRunner {

	private final LinkSystem system;
	private final Map<String, LinkModelSet> sets = new HashMap<String, LinkModelSet>();
	private final Map<String, LinkChangesRunnerInstance> instances = new HashMap<String, LinkChangesRunnerInstance>();

	private boolean waitingSave = false;

	public LinkChangesRunner(LinkSystem system) {
		this.system = system;
	}

	public LinkSystem getSystem() {
		return this.system;
	}

	public Map<String, LinkModelSet> getSets() {
		return this.sets;
	}

	// Runner
	public void runChanges(List<LinkChange> changes) {
		if (this.waitingSave) {
			throw new RuntimeException("Previous changes are still waiting to be saved.");
		}

		HashMapWithList<String, LinkChange> changeMap = this.divideChanges(changes);

		for (String type : changeMap.getKeySet()) {
			List<LinkChange> changeList = changeMap.getObjects(type);

			LinkChangesRunnerInstance instance = new LinkChangesRunnerInstance(type, changeList);
			instance.run();
			this.instances.put(type, instance);
		}

		this.waitingSave = true;
	}

	public void saveChanges() {
		if (this.waitingSave == false) {
			throw new RuntimeException("No changes have been run yet.");
		}

		for (LinkChangesRunnerInstance instance : this.instances.values()) {
			instance.save();
		}
	}

	/**
	 * @return true if any of the submitted changes have resulted in a failure.
	 */
	public boolean hasFailure() {
		return this.getFailures().isEmpty() == false;
	}

	public List<LinkChangeException> getFailures() {
		List<LinkChangeException> failures = new ArrayList<LinkChangeException>();

		for (LinkChangesRunnerInstance instance : this.instances.values()) {
			failures.addAll(instance.failures);
		}

		return failures;
	}

	// Internal
	private HashMapWithList<String, LinkChange> divideChanges(List<LinkChange> changes) {
		HashMapWithList<String, LinkChange> map = new HashMapWithList<String, LinkChange>();

		for (LinkChange change : changes) {
			String type = change.getPrimaryType();
			map.add(type, change);
		}

		return map;
	}

	private class LinkChangesRunnerInstance {

		private final String type;
		private final List<LinkChange> changes;
		private final LinkModelSet linkSet;

		private List<ModelKey> missingKeys = new ArrayList<ModelKey>();
		private List<LinkChangeException> failures = new ArrayList<LinkChangeException>();

		public LinkChangesRunnerInstance(String type, List<LinkChange> changes) {
			this.type = type;
			this.changes = changes;
			this.linkSet = this.loadSet();
		}

		public void run() {
			for (LinkChange change : this.changes) {
				ModelKey key = change.getPrimaryKey();
				LinkModel linkModel = this.linkSet.getModelForKey(key);

				if (linkModel == null) {
					this.missingKeys.add(key);
				} else {
					String targetName = change.getLinkName();

					Link modelLink = linkModel.getLink(targetName);
					LinkChangeAction action = change.getAction();

					Collection<ModelKey> targetKeys = change.getTargetKeys();
					Relation relation = new RelationImpl(targetKeys);

					try {
						switch (action) {
							case LINK:
								modelLink.addRelation(relation);
								break;
							case UNLINK:
								modelLink.removeRelation(relation);
								break;
							case CLEAR:
								modelLink.clearRelations();
								break;
						}
					} catch (RelationChangeException | UnavailableLinkException e) {
						LinkChangeException failure = new LinkChangeException(change, e);
						this.failures.add(failure);
					}
				}
			}
		}

		public void save() {
			this.linkSet.save();
		}

		private LinkModelSet loadSet() {
			LinkModelSet linkSet = LinkChangesRunner.this.system.loadSet(this.type);
			List<ModelKey> keys = new ArrayList<ModelKey>();

			for (LinkChange change : this.changes) {
				ModelKey key = change.getPrimaryKey();
				keys.add(key);
			}

			linkSet.loadModels(keys);
			return linkSet;
		}

	}

}
