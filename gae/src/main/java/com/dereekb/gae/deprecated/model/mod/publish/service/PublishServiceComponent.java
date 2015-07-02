package com.thevisitcompany.gae.deprecated.model.mod.publish.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.ForbiddenObjectChangesException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.exceptions.UnavailableObjectsException;
import com.thevisitcompany.gae.deprecated.model.mod.cruds.service.ReadService;
import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishAction;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishFunction;
import com.thevisitcompany.gae.deprecated.model.mod.publish.functions.PublishPair;
import com.thevisitcompany.gae.server.datastore.models.DatabaseModel;

public class PublishServiceComponent<T extends KeyedPublishableModel<Long>>
        implements PublishService<T, Long> {

	private final ReadService<T, Long> readService;
	private final PublishFunction<T> publishFunction;

	public PublishServiceComponent(ReadService<T, Long> readService, PublishFunction<T> publishFunction) {
		this.readService = readService;
		this.publishFunction = publishFunction;
	}

	public List<T> publishChange(Collection<PublishPair<T>> pairs) {
		this.publishFunction.addObjects(pairs);
		boolean success = this.publishFunction.run();

		List<T> changed = new ArrayList<T>();

		if (success) {
			for (PublishPair<T> pair : pairs) {
				if (pair.getResult()) {
					changed.add(pair.getSource());
				}
			}
		}

		return changed;
	}

	@Override
	public Collection<Long> publishChange(Iterable<T> input,
	                                      PublishAction action) throws ForbiddenObjectChangesException {

		List<PublishPair<T>> pairs = new ArrayList<PublishPair<T>>();

		for (T item : input) {
			PublishPair<T> pair = new PublishPair<T>(item, action);
			pairs.add(pair);
		}

		List<T> results = this.publishChange(pairs);
		List<Long> changedIdentifiers = DatabaseModel.readModelIdentifiers(results);
		return changedIdentifiers;
	}

	@Override
	public Collection<Long> publishChangeWithIds(Iterable<Long> input,
	                                             PublishAction action)
	        throws ForbiddenObjectChangesException,
	            UnavailableObjectsException {
		Collection<T> models = this.readService.read(input);
		return this.publishChange(models, action);
	}

}
