package com.dereekb.gae.web.api.util.attribute;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;

/**
 * {@link InvalidAttribute} extension that is {@link AlwaysKeyed}.
 * 
 * @author dereekb
 */
public interface KeyedInvalidAttribute
        extends InvalidAttribute, AlwaysKeyed<UniqueModel> {

}
