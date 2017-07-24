package com.dereekb.gae.model.extension.links.system.modification.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationPair;
import com.dereekb.gae.model.extension.links.system.modification.components.LinkModification;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.map.CaseInsensitiveMapWithList;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.collections.map.MapUtility;

public class LinkModificationPairUtility {

	public static Map<String, HashMapWithList<ModelKey, LinkModificationPair>> buildTypeAndKeyedChangesMap(List<LinkModificationPair> linkModificationPairs) {
		CaseInsensitiveMapWithList<LinkModificationPair> typesMap = buildTypeChangesMap(linkModificationPairs);

		Map<String, HashMapWithList<ModelKey, LinkModificationPair>> typeKeyedMap = new HashMap<String, HashMapWithList<ModelKey, LinkModificationPair>>();

		for (Entry<String, List<LinkModificationPair>> typesEntry : typesMap.entrySet()) {
			HashMapWithList<ModelKey, LinkModificationPair> keyedMap = buildKeyedChangesMap(typesEntry.getValue());
			typeKeyedMap.put(typesEntry.getKey(), keyedMap);
		}

		return typeKeyedMap;
	}

	public static CaseInsensitiveMapWithList<LinkModificationPair> buildTypeChangesMap(List<LinkModificationPair> linkModificationPairs) {
		CaseInsensitiveMapWithList<LinkModificationPair> typesMap = new CaseInsensitiveMapWithList<LinkModificationPair>();

		for (LinkModificationPair modificationPair : linkModificationPairs) {
			LinkModification modification = modificationPair.getLinkModification();
			String type = modification.getLinkModelType();
			typesMap.add(type, modificationPair);
		}

		return typesMap;
	}

	public static HashMapWithList<ModelKey, LinkModificationPair> buildKeyedChangesMap(List<LinkModificationPair> linkModificationPairs) {
		HashMapWithList<ModelKey, LinkModificationPair> keyedMap = MapUtility
		        .makeHashMapWithList(linkModificationPairs);
		return keyedMap;
	}
	
}
