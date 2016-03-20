package com.dereekb.gae.test.applications.api.model.extension.links;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.model.extension.links.components.system.LinkSystem;
import com.dereekb.gae.model.extension.links.components.system.LinkSystemEntry;
import com.dereekb.gae.model.extension.links.service.LinkService;
import com.dereekb.gae.test.applications.api.ApiApplicationTestContext;

/**
 * Tests the configured {@link LinkService}.
 *
 * @author dereekb
 *
 */
public class LinkSystemTest extends ApiApplicationTestContext {

	@Autowired
	@Qualifier("linkSystem")
	private LinkSystem system;

	private ArrayList<? extends LinkSystemEntry> definitions;

	public LinkSystem getSystem() {
		return this.system;
	}

	public void setSystem(LinkSystem system) {
		this.system = system;
	}

	public Object getDefinitions() {
		return this.definitions;
	}

	@Autowired
	@Qualifier("linkSystemEntries")
	public void setDefinitions(Object definitions) {

		@SuppressWarnings("unchecked")
		ArrayList<? extends LinkSystemEntry> cast = (ArrayList<? extends LinkSystemEntry>) definitions;
		this.definitions = cast;
	}

	@Test
	public void testServiceEntitiesSice() {
		Set<String> types = this.system.getAvailableSetTypes();
		Assert.assertTrue(types.size() == this.definitions.size());
	}

}
