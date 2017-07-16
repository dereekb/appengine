package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegateInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * {@link LinkModificationSystemDelegate} implementation.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemDelegateImpl
        implements LinkModificationSystemDelegate {

	private CaseInsensitiveMap<LinkModificationSystemEntry> systemEntries;

	public LinkModificationSystemDelegateImpl(Map<String, LinkModificationSystemEntry> systemEntries) {
		super();
		this.setSystemEntries(systemEntries);
	}

	public Map<String, LinkModificationSystemEntry> getSystemEntries() {
		return this.systemEntries;
	}

	public void setSystemEntries(Map<String, LinkModificationSystemEntry> systemEntries) {
		if (systemEntries == null) {
			throw new IllegalArgumentException("systemEntries cannot be null.");
		}
	
		this.systemEntries = new CaseInsensitiveMap<LinkModificationSystemEntry>(systemEntries);
	}

	// MARK: LinkModificationSystemDelegate
	@Override
	public LinkModificationSystemDelegateInstance makeInstance() {
		return new LinkModificationSystemDelegateInstanceImpl();
	}

	// MARK: Internal
	protected LinkModificationSystemEntryInstance makeInstanceForType(String type) {
		LinkModificationSystemEntry entry = this.systemEntries.get(type);

		if (entry == null) {
			throw new RuntimeException(); 	// TODO: Replace this.
		}

		return entry.makeInstance();
	}

	// MARK: LinkModificationSystemDelegateInstance
	protected class LinkModificationSystemDelegateInstanceImpl extends AbstractLinkModificationSystemChangeInstance
	        implements LinkModificationSystemDelegateInstance {

		private CaseInsensitiveMap<LinkModificationSystemEntryInstance> entryInstances = new CaseInsensitiveMap<LinkModificationSystemEntryInstance>();

		@Override
		public LinkModificationResultSet performModificationsForType(String type,
		                                                             HashMapWithList<ModelKey, LinkModification> keyedMap) {

			LinkModificationSystemEntryInstance entryInstance = this.getEntryInstance(type);
			return entryInstance.performModifications(keyedMap);
		}

		// MARK: Internal
		private LinkModificationSystemEntryInstance getEntryInstance(String type) {
			LinkModificationSystemEntryInstance instance = this.entryInstances.get(type);

			if (instance == null) {
				instance = LinkModificationSystemDelegateImpl.this.makeInstanceForType(type);
			}

			return instance;
		}

		// MARK: AbstractLinkModificationSystemChangeInstance
		@Override
		public Iterable<? extends LinkModificationSystemChangeInstance> getInstances() {
			return this.entryInstances.values();
		}

	}

}
