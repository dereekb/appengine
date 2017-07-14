package com.dereekb.gae.test.model.extension.link;

import org.junit.Before;
import org.junit.Test;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystem;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemDelegate;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemInstance;
import com.dereekb.gae.model.extension.links.system.modification.impl.LinkModificationSystemImpl;
import com.dereekb.gae.test.model.extension.link.LinkSystemTests.LinkSystemCreationInfo;

/**
 * Unit tests for {@link LinkModificationSystemImpl}.
 * 
 * @author dereekb
 *
 */
public class LinkModificationSystemTests {
	
	private LinkModificationSystem system;
	
	@Before
	public void initSystem() {
		this.system = makeLinkModificationSystem();
	}
	
	public static LinkModificationSystemImpl makeLinkModificationSystem() {
		LinkSystemCreationInfo testLinkSystem = LinkSystemTests.makeTestLinkSystem();
		LinkModificationSystemDelegate delegate = null;
		
		LinkModificationSystemImpl linkModificationSystem = new LinkModificationSystemImpl(testLinkSystem.linkSystem, delegate);
		return linkModificationSystem;
	}

	@Test
	public void testSystemInstances() {
		LinkModificationSystemInstance instance = this.system.makeInstance();

		// TODO: ...
	}
	
}
