package com.thevisitcompany.gae.deprecated.model.storage.support;

import com.thevisitcompany.gae.deprecated.model.storage.StorageModel;
import com.thevisitcompany.gae.model.objectify.SearchableObjectifyFunctionsServiceFactory;

public abstract class StorageModelFunctionsServiceFactory<S extends StorageModelFunctionsService<T>, T extends StorageModel<T>> extends SearchableObjectifyFunctionsServiceFactory<S, T> {

}
