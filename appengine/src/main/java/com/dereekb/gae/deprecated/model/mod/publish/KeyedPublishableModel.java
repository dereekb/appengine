package com.thevisitcompany.gae.deprecated.model.mod.publish;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.server.datastore.models.deprecated.KeyedModel;
import com.thevisitcompany.visit.models.info.request.Request;

public interface KeyedPublishableModel<K> extends KeyedModel<K>, Publishable {

	public Key<Request> getPublishRequest();

	public void setPublishRequest(Key<Request> publishRequest);

}
