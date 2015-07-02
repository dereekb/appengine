package com.thevisitcompany.gae.deprecated.model.mod.publish;

import com.googlecode.objectify.Key;
import com.thevisitcompany.visit.models.info.request.Request;

/**
 * Publishable model that can be modified, and have publish requests.
 * @author dereekb
 * @deprecated Replaced by KeyedPublishableModel
 */
@Deprecated
public interface PublishableModel extends Publishable {

	public Key<Request> getPublishRequest();

	public void setPublishRequest(Key<Request> publishRequest);

	public void setPublished(Boolean publish);
	
	public Long getId();
	
}
