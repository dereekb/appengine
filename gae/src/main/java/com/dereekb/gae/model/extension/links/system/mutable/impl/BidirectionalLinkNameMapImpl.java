package com.dereekb.gae.model.extension.links.system.mutable.impl;

import java.util.Map;

import com.dereekb.gae.model.extension.links.system.mutable.BidirectionalLinkNameMap;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoDynamicReverseLinksException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.NoReverseLinksException;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMap;

/**
 * {@link BidirectionalLinkNameMap} implementation.
 * 
 * @author dereekb
 *
 */
public class BidirectionalLinkNameMapImpl
        implements BidirectionalLinkNameMap {

	private CaseInsensitiveMap<String> linkMap;
	
	/**
	 * Nested Map that is keyed by LinkName, then Type.
	 */
	private CaseInsensitiveMap<CaseInsensitiveMap<String>> dynamicLinkMap;

	
	public BidirectionalLinkNameMapImpl(Map<String, String> linkNames) {
		this.setLinkMap(linkNames);
	}

	public Map<String, String> getLinkMap() {
		return this.linkMap;
	}

	public void setLinkMap(Map<String, String> linkMap) {
		if (linkMap == null) {
			throw new IllegalArgumentException("linkMap cannot be null.");
		}

		this.linkMap = new CaseInsensitiveMap<String>(linkMap);
	}
	
	public Map<String, ? extends Map<String, String>> getDynamicLinkMap() {
		return this.dynamicLinkMap;
	}
	
	public void setDynamicLinkMap(Map<String, ? extends Map<String, String>> dynamicLinkMap) {
		if (dynamicLinkMap == null) {
			throw new IllegalArgumentException("dynamicLinkMap cannot be null.");
		}

		this.dynamicLinkMap = CaseInsensitiveMap.makeNestedMap(dynamicLinkMap);
	}

	// MARK: BidirectionalLinkNameMap
	@Override
	public boolean isBidirectionallyLinked(String linkName) {
		return this.linkMap.containsKey(linkName);
	}

	@Override
	public String getReverseLinkName(String linkName) throws NoReverseLinksException {
		String reverseLinkName = this.linkMap.get(linkName);
		
		if (reverseLinkName == null) {
			throw new NoReverseLinksException(reverseLinkName);
		}
		
		return reverseLinkName;
	}

	@Override
	public String getDynamicReverseLinkName(String dynamicLinkName,
	                                        String relationLinkType)
	        throws NoDynamicReverseLinksException, NoReverseLinksException {
		
		CaseInsensitiveMap<String> typesLinkNamesMap = this.getTypesLinkNamesMapForDynamicLinkName(dynamicLinkName);
		String reverseLinkName = typesLinkNamesMap.get(relationLinkType);
		
		if (reverseLinkName == null) {
			throw new NoReverseLinksException(dynamicLinkName, relationLinkType);
		}
		
		return reverseLinkName;
	}
	
	// MARK: Internal
	protected CaseInsensitiveMap<String> getTypesLinkNamesMapForDynamicLinkName(String dynamicLinkName) throws NoDynamicReverseLinksException {
		CaseInsensitiveMap<String> typesLinkNamesMap = this.dynamicLinkMap.get(dynamicLinkName);
		
		if (typesLinkNamesMap == null) {
			throw new NoDynamicReverseLinksException(dynamicLinkName);
		}
		
		return typesLinkNamesMap;
	}

	@Override
	public String toString() {
		return "BidirectionalLinkNameMapImpl [linkMap=" + this.linkMap + ", dynamicLinkMap=" + this.dynamicLinkMap
		        + "]";
	}
	
}
