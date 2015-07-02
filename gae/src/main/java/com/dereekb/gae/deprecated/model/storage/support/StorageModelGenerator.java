package com.thevisitcompany.gae.deprecated.model.storage.support;

import com.thevisitcompany.gae.deprecated.model.general.LocationGenerator;
import com.thevisitcompany.gae.model.objectify.generator.ObjectifyModelGenerator;
import com.thevisitcompany.gae.server.datastore.objectify.ObjectifyModel;

public abstract class StorageModelGenerator<T extends ObjectifyModel<T>> extends ObjectifyModelGenerator<T> {

	protected final LocationGenerator locationGenerator = new LocationGenerator();

}
