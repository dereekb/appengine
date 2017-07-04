package com.dereekb.gae.model.extension.links.system.mutable;

import com.dereekb.gae.model.extension.links.system.components.LimitedLinkInfo;
import com.dereekb.gae.model.extension.links.system.components.LinkSize;

/**
 * An abstract link that isn't directly associated with a link model like
 * {@link LimitedLinkInfo}.
 * 
 * @author dereekb
 *
 */
public interface MutableLinkData<T> {

	public String getLinkName();

	public String getLinkType();

	public LinkSize getLinkSize();

	public MutableLinkAccessor makeLinkAccessor(T model);

}
