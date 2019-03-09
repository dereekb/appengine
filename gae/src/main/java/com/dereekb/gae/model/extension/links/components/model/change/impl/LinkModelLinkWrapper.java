package com.dereekb.gae.model.extension.links.components.model.change.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.extension.links.components.Link;
import com.dereekb.gae.model.extension.links.components.LinkData;
import com.dereekb.gae.model.extension.links.components.LinkTarget;
import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.components.RelationResult;
import com.dereekb.gae.model.extension.links.components.exception.RelationChangeException;
import com.dereekb.gae.model.extension.links.components.exception.UnavailableLinkException;
import com.dereekb.gae.model.extension.links.components.model.LinkModel;
import com.dereekb.gae.model.extension.links.components.model.change.LinkChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for wrapping generated {@link Link} values for {@link LinkModel}. Is
 * used to capture link changes made to a {@link LinkModel}.
 *
 * @author dereekb
 *
 */
public class LinkModelLinkWrapper
        implements Link, LinkChange {

	private final Link link;
	private final LinkModel linkModel;

	private Set<ModelKey> added = new HashSet<ModelKey>();
	private Set<ModelKey> removed = new HashSet<ModelKey>();

	public LinkModelLinkWrapper(LinkModel linkModel, Link link) {
		this.link = link;
		this.linkModel = linkModel;
	}

	@Override
    public Link getLink() {
		return this.link;
	}

	public LinkModel getLinkModel() {
		return this.linkModel;
	}

	@Override
	public Set<ModelKey> getAddedKeys() {
		return this.added;
	}

	@Override
	public Set<ModelKey> getRemovedKeys() {
		return this.removed;
	}

	@Override
	public boolean hasChanges() {
		return !this.added.isEmpty() || !this.removed.isEmpty();
	}

	// MARK: Link
	@Override
	public String getLinkName() {
		return this.link.getLinkName();
	}

	@Override
	public ModelKey getLinkModelKey() {
		return this.link.getLinkModelKey();
	}

	@Override
	public LinkTarget getLinkTarget() {
		return this.link.getLinkTarget();
	}

	// MARK: Link
	@Override
	public LinkData getLinkData() {
		return this.link.getLinkData();
	}

	@Override
	public RelationResult setRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		RelationResult result = this.link.setRelation(change);

		Set<ModelKey> added = new HashSet<ModelKey>(change.getRelationKeys());
		added.remove(result.getRedundant());
		this.addedKeys(added);

		Set<ModelKey> removed = result.getHits(); // This returns all values.
		removed.remove(result.getRedundant()); // Remove the redundant values.
		this.removedKeys(removed);

		return result;
	}

	@Override
	public RelationResult addRelation(Relation change) throws RelationChangeException, UnavailableLinkException {
		RelationResult result = this.link.addRelation(change);
		this.addedKeys(result.getHits());
		return result;
	}

	@Override
	public RelationResult removeRelation(Relation change) throws RelationChangeException {
		RelationResult result = this.link.removeRelation(change);
		this.removedKeys(result.getHits());
		return result;
	}

	@Override
	public RelationResult clearRelations() {
		RelationResult result = this.link.clearRelations();

		Set<ModelKey> removed = result.getHits();
		this.removedKeys(removed);

		return result;
	}

	// MARK: Internal
	/**
	 * Mark the keys as added, adding them to {@link #added} and removing them
	 * from {@link #removed} if they exist.
	 *
	 * @param keys
	 */
	private void addedKeys(Collection<ModelKey> keys) {
		this.added.addAll(keys);
		this.removed.removeAll(keys);
	}

	/**
	 * Mark the keys as removed, adding them to {@link #removed} and removing
	 * them from {@link #added} if they exist.
	 *
	 * @param keys
	 */
	private void removedKeys(Collection<ModelKey> keys) {
		this.removed.addAll(keys);
		this.added.removeAll(keys);
	}

	@Override
	public String toString() {
		return "LinkModelLinkWrapper [link=" + this.link + ", linkModel=" + this.linkModel + ", added=" + this.added
		        + ", removed=" + this.removed + "]";
	}

	// MARK: Wrapper
	/**
	 * Wraps the {@link Link} values passed in the input map.
	 *
	 * @param model
	 *            {@link LinkModel} owning the {@link Link} values.
	 * @param links
	 *            {@link Map} with {@link Link} values.
	 * @return {@link WrapperPair} containing wrapped values.
	 */
	public static WrapperPair wrapLinks(LinkModel model,
	                                    Map<String, Link> links) {

		// Wrap Links
		Map<String, Link> wrappedLinks = new HashMap<String, Link>();
		Map<String, LinkChange> changes = new HashMap<String, LinkChange>();

		for (Entry<String, Link> entry : links.entrySet()) {
			String key = entry.getKey();
			Link link = entry.getValue();

			LinkModelLinkWrapper wrappedLink = new LinkModelLinkWrapper(model, link);
			wrappedLinks.put(key, wrappedLink);
			changes.put(key, wrappedLink);
		}

		LinkModelChangeImpl linkChanges = new LinkModelChangeImpl(model, changes);
		WrapperPair pair = new WrapperPair(wrappedLinks, linkChanges);
		return pair;
	}

	/**
	 * Used by {@link LinkModelLinkWrapper#wrapLinks(LinkModel, Map)}.
	 *
	 * @author dereekb
	 */
	public static class WrapperPair {

		private final Map<String, Link> wrappedLinks;
		private final LinkModelChangeImpl linkChanges;

		private WrapperPair(Map<String, Link> wrappedLinks, LinkModelChangeImpl linkChanges) {
			this.wrappedLinks = wrappedLinks;
			this.linkChanges = linkChanges;
		}

		public Map<String, Link> getWrappedLinks() {
			return this.wrappedLinks;
		}

		public LinkModelChangeImpl getLinkChanges() {
			return this.linkChanges;
		}

	}

}
