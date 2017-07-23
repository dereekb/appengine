package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemChangeInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegateInstance;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntry;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemEntryInstance;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResultSet;
import com.dereekb.gae.model.extension.links.system.modification.exception.UndoChangesAlreadyExecutedException;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithSet;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.TwoDimensionalCaseInsensitiveMapWithSet;

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
	public void preTestModifications(List<LinkModificationPreTestPair> testPairs) {
		CaseInsensitiveMapWithSet<ModelKey> keysMap = new CaseInsensitiveMapWithSet<ModelKey>();

		TwoDimensionalCaseInsensitiveMapWithSet<ModelKey, LinkModificationPreTestPair> primaryKeysMap = new TwoDimensionalCaseInsensitiveMapWithSet<ModelKey, LinkModificationPreTestPair>();
		TwoDimensionalCaseInsensitiveMapWithSet<ModelKey, LinkModificationPreTestPair> secondaryKeysMap = new TwoDimensionalCaseInsensitiveMapWithSet<ModelKey, LinkModificationPreTestPair>();
		
		for (LinkModificationPreTestPair pair : testPairs) {
			LinkModification modification = pair.getLinkModification();
			
			String mainType = modification.getLinkModelType();
			ModelKey mainKey = modification.getKey();
			
			keysMap.add(mainType, mainKey);	// Add the main key.
			
			// Add to the primary map
			primaryKeysMap.add(mainType, mainKey, pair);
			
			MutableLinkChange mutableLinkChange = modification.getChange();
			
			switch (mutableLinkChange.getLinkChangeType()) {
				case SET:
				case ADD:
					LinkInfo link = modification.getLink();
					Set<ModelKey> targetKeys = mutableLinkChange.getKeys();
					
					try {
						String reverseModelType = link.getRelationLinkType();
						
						// Add all to the reverse.
						keysMap.addAll(reverseModelType, targetKeys);
						
						// Add all to the secondary map.
						secondaryKeysMap.addAll(reverseModelType, targetKeys, pair);
					} catch (DynamicLinkInfoException e) {
						// Ignore dynamic links.
					}
					break;
				case REMOVE:
				case CLEAR:
					// Reverse is optional. Don't worry about loading these.
					break;
				case NONE:
				default:
					break;	
			}
		}
		
		// Using the keys map, assert all models exist.
		for (Entry<String, Set<ModelKey>> entry : keysMap.entrySet()) {
			String modelType = entry.getKey();
			Set<ModelKey> keys = entry.getValue();
			
			LinkModificationSystemEntry systemEntry = this.getEntryForType(modelType);
			systemEntry.filterModelsExist(keys);
		}
	}

	@Override
	public LinkModificationSystemDelegateInstance makeInstance() {
		return new LinkModificationSystemDelegateInstanceImpl();
	}

	// MARK: Internal
	protected LinkModificationSystemEntryInstance makeInstanceForType(String type) throws UnavailableLinkModelException {
		LinkModificationSystemEntry entry = this.getEntryForType(type);
		return entry.makeInstance();
	}
	
	protected LinkModificationSystemEntry getEntryForType(String type) throws UnavailableLinkModelException {
		LinkModificationSystemEntry entry = this.systemEntries.get(type);
		
		if (entry == null) {
			throw UnavailableLinkModelException.makeForType(type);
		}
		
		return entry;
	}

	// MARK: LinkModificationSystemDelegateInstance
	protected class LinkModificationSystemDelegateInstanceImpl extends AbstractLinkModificationSystemChangeInstance
	        implements LinkModificationSystemDelegateInstance {

		private CaseInsensitiveMap<LinkModificationSystemEntryInstance> entryInstances = new CaseInsensitiveMap<LinkModificationSystemEntryInstance>();

		@Override
		public LinkModificationResultSet performModificationsForType(String type,
		                                                             HashMapWithList<ModelKey, LinkModificationPair> keyedMap)
		        throws UndoChangesAlreadyExecutedException,
		            UnavailableLinkModelException {
			LinkModificationSystemEntryInstance entryInstance = this.getEntryInstance(type);
			return entryInstance.performModifications(keyedMap);
		}

		// MARK: Internal
		private LinkModificationSystemEntryInstance getEntryInstance(String type) {
			LinkModificationSystemEntryInstance instance = this.entryInstances.get(type);

			if (instance == null) {
				instance = LinkModificationSystemDelegateImpl.this.makeInstanceForType(type);
				this.entryInstances.put(type, instance);
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
