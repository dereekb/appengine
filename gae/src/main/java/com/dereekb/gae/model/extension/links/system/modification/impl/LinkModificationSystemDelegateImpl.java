package com.dereekb.gae.model.extension.links.system.modification.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.extension.links.system.components.LinkInfo;
import com.dereekb.gae.model.extension.links.system.components.exceptions.DynamicLinkInfoException;
import com.dereekb.gae.model.extension.links.system.components.exceptions.UnavailableLinkModelException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestPair;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPreTestResultInfo;
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
import com.dereekb.gae.utilities.filters.FilterResults;

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
	public void preTestModifications(List<LinkModificationPreTestPair> pairs) {
		CaseInsensitiveMapWithSet<ModelKey> keysMap = new CaseInsensitiveMapWithSet<ModelKey>();
		List<TestPair> testPairs = new ArrayList<TestPair>();
		
		for (LinkModificationPreTestPair pair : pairs) {
			TestPair testPair = new TestPair(pair);
			testPairs.add(testPair);
			
			LinkModification modification = pair.getLinkModification();
			
			String mainType = modification.getLinkModelType();
			ModelKey mainKey = modification.getKey();
			
			keysMap.add(mainType, mainKey);	// Add the main key.
			
			// Update Test Pair
			testPair.setMainKey(mainType, mainKey);
			
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

						// Update Test Pair
						testPair.setSecondaryKeys(reverseModelType, targetKeys);
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
		
		// Find which models exist.
		CaseInsensitiveMapWithSet<ModelKey> existsMap = new CaseInsensitiveMapWithSet<ModelKey>();

		for (Entry<String, Set<ModelKey>> entry : keysMap.entrySet()) {
			String modelType = entry.getKey();
			Set<ModelKey> keys = entry.getValue();

			LinkModificationSystemEntry systemEntry = this.getEntryForType(modelType);
			FilterResults<ModelKey> keyResults = systemEntry.filterModelsExist(keys);
			
			// Add Existing
			List<ModelKey> existing = keyResults.getPassingObjects();
			existsMap.addAll(modelType, existing);
			
			// Update Unavailable
			//List<ModelKey> unavailable = keyResults.getFailingObjects();			
		}
		
		// Update Pairs
		for (TestPair testPair : testPairs) {
			testPair.updateWithExistsMap(existsMap);
		}
	}

	private class TestPair {
		
		private final LinkModificationPreTestPair pair;
		
		private String mainType;
		private ModelKey mainKey;
		
		private String secondaryType;
		private Set<ModelKey> secondaryKeys = Collections.emptySet();
		
		public TestPair(LinkModificationPreTestPair pair) {
			this.pair = pair;
		}

		public void setMainKey(String mainType,
		                       ModelKey mainKey) {
			this.mainType = mainType;
			this.mainKey = mainKey;
		}

		public void setSecondaryKeys(String secondaryType, 
		                             Set<ModelKey> secondaryKeys) {
			this.secondaryType = secondaryType;
			this.secondaryKeys = new HashSet<ModelKey>(secondaryKeys);
		}
		
		public void updateWithExistsMap(CaseInsensitiveMapWithSet<ModelKey> existsMap) {
			Set<ModelKey> primaryExisting = existsMap.get(this.mainType);
			
			boolean isMissingPrimaryKey = primaryExisting.contains(this.mainKey) == false;
			
			if (this.secondaryType != null) {
				Set<ModelKey> secondaryExisting = existsMap.get(this.secondaryType);
				
				// Remove from secondary keys.
				this.secondaryKeys.removeAll(secondaryExisting);
				
			}
			
			// Create Result Info
			LinkModificationPreTestResultInfo resultInfo = new LinkModificationPreTestResultInfoImpl(isMissingPrimaryKey, this.secondaryKeys);
			this.pair.setResultInfo(resultInfo);
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
		                                                             HashMapWithList<ModelKey, LinkModificationPair> keyedMap, boolean atomic)
		        throws UndoChangesAlreadyExecutedException,
		            UnavailableLinkModelException {
			LinkModificationSystemEntryInstance entryInstance = this.getEntryInstance(type);
			return entryInstance.performModifications(keyedMap, atomic);
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
