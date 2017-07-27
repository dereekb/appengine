package com.dereekb.gae.model.extension.links.system.modification.utility;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequestReference;

public class LinkModificationSystemUtility {

	public static List<LinkModificationSystemRequest> readRequests(Iterable<? extends LinkModificationSystemRequestReference> references) {
		List<LinkModificationSystemRequest> list = new ArrayList<LinkModificationSystemRequest>();
		
		for (LinkModificationSystemRequestReference reference : references) {
			list.add(reference.getRequest());
		}
		
		return list;
	}
	
}
