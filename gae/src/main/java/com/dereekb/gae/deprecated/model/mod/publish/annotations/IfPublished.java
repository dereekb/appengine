package com.thevisitcompany.gae.deprecated.model.mod.publish.annotations;

import com.googlecode.objectify.condition.PojoIf;
import com.thevisitcompany.gae.deprecated.model.mod.publish.Publishable;

/**
 * Objectify Save Condition.
 * @author dereekb
 *
 */
public class IfPublished extends PojoIf<Publishable> {

	@Override
	public boolean matchesPojo(Publishable publishable) {
		return publishable.isPublished();
	}
	
}
