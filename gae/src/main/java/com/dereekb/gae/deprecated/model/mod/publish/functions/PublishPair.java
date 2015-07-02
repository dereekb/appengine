package com.thevisitcompany.gae.deprecated.model.mod.publish.functions;

import com.googlecode.objectify.Key;
import com.thevisitcompany.gae.deprecated.model.mod.publish.KeyedPublishableModel;
import com.thevisitcompany.gae.utilities.collections.map.HashMapWithList;
import com.thevisitcompany.gae.utilities.collections.pairs.SuccessResultsPair;
import com.thevisitcompany.gae.utilities.function.staged.components.StagedFunctionStage;
import com.thevisitcompany.visit.models.info.request.Request;

/**
 * Basic publishing pair.
 * Result is boolean of whether or not the object completed it's task.
 * 
 * @author dereekb
 * @param <T>
 *            Model type being deleted.
 */
public class PublishPair<T extends KeyedPublishableModel<Long>> extends SuccessResultsPair<T>
        implements KeyedPublishableModel<Long> {

	private final PublishAction action;
	private boolean doImmediately;

	public PublishPair(T object, PublishAction action) {
		super(object);
		this.action = action;
		this.object = false;
	}

	public PublishAction getAction() {
		return action;
	}

	public boolean doFunction() {
		return this.object;
	}

	@Override
	public T getFunctionObject(StagedFunctionStage stage) {
		return this.getSource();
	}

	@Override
	public boolean hasFailed() {
		return !this.object;
	}

	public static <T extends KeyedPublishableModel<Long>> HashMapWithList<PublishAction, PublishPair<T>> getActionPairsMap(Iterable<PublishPair<T>> pairs) {
		HashMapWithList<PublishAction, PublishPair<T>> map = new HashMapWithList<PublishAction, PublishPair<T>>();

		for (PublishPair<T> pair : pairs) {
			PublishAction action = pair.getAction();
			map.add(action, pair);
		}

		return map;
	}

	@Override
	public Long getId() {
		return this.key.getId();
	}

	@Override
	public void setPublished(boolean publish) {
		this.key.setPublished(publish);
	}

	@Override
	public boolean isPublished() {
		return this.key.isPublished();
	}

	@Override
	public Key<Request> getPublishRequest() {
		return this.key.getPublishRequest();
	}

	@Override
	public void setPublishRequest(Key<Request> publishRequest) {
		this.key.setPublishRequest(publishRequest);
	}

	/**
	 * Returns true if the action should be done immediately instead of requiring a request.
	 */
	public boolean doImmediately() {
		return doImmediately;
	}

	public void setDoImmediately(boolean doImmediately) {
		this.doImmediately = doImmediately;
	}

	@Override
	public String toString() {
		return "PublishPair [action=" + action + ", doImmediately=" + doImmediately + ", key=" + key + ", object="
		        + object + "]";
	}
}
