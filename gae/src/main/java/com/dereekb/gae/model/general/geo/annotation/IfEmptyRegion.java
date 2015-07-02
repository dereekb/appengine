package com.dereekb.gae.model.general.geo.annotation;

import java.lang.reflect.Field;

import com.dereekb.gae.model.general.geo.Region;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.condition.InitializeIf;
import com.googlecode.objectify.condition.ValueIf;

/**
 * Returns true if the {@link Region} has no points in it.
 *
 * @author dereekb
 */
public class IfEmptyRegion extends ValueIf<Region>
        implements InitializeIf {

	@Override
    public void init(ObjectifyFactory fact,
	                 Field field) {}

	@Override
	public boolean matchesValue(Region value) {
		return value.isEmpty();
	}

}
