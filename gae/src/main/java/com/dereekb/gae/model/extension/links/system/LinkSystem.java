package com.dereekb.gae.model.extension.links.system;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkSystem;
import com.dereekb.gae.model.extension.links.system.readonly.ReadOnlyLinkSystem;

/**
 * Full link system that implements both {@link MutableLinkSystem} and
 * {@link ReadOnlyLinkSystem}.
 * 
 * @author dereekb
 *
 */
public interface LinkSystem
        extends MutableLinkSystem, ReadOnlyLinkSystem {

}
