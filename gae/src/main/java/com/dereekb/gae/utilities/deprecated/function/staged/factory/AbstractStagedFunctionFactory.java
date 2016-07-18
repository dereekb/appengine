package com.dereekb.gae.utilities.function.staged.factory;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.utilities.deprecated.function.staged.StagedFunction;
import com.dereekb.gae.utilities.deprecated.function.staged.components.StagedFunctionObject;
import com.dereekb.gae.utilities.deprecated.function.staged.delegates.StagedFunctionSaveDelegate;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.observer.StagedFunctionObjectObserverFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.observer.StagedFunctionObserverFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.observer.StagedFunctionObserverMapFactory;
import com.dereekb.gae.utilities.deprecated.function.staged.factory.pairs.StagedFunctionStagePair;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObjectObserver;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObserver;
import com.dereekb.gae.utilities.deprecated.function.staged.observer.StagedFunctionObserverMap;
import com.dereekb.gae.utilities.factory.Factory;

public abstract class AbstractStagedFunctionFactory<F extends StagedFunction<T, W>, T, W extends StagedFunctionObject<T>>
        implements StagedFunctionObserverMapFactory<T, W>, Factory<F> {

	private StagedFunctionSaveDelegate<T> defaultSaveDelegate;
	private StagedFunctionObserverMapFactory<T, W> observerMapFactory = this;
	private List<StagedFunctionObserverFactory<StagedFunctionObserver<T>, T>> observerFactories = new ArrayList<StagedFunctionObserverFactory<StagedFunctionObserver<T>, T>>();
	private List<StagedFunctionObjectObserverFactory<StagedFunctionObjectObserver<T, W>, T, W>> objectObserverFactories = new ArrayList<StagedFunctionObjectObserverFactory<StagedFunctionObjectObserver<T, W>, T, W>>();

	/**
	 * Generates a new, blank function handler of the type <F>.
	 */
	protected abstract F newStagedFunction();

	@Override
	public F make() {
		return this.makeStagedFunction();
	}

	protected F makeStagedFunction() {
		F functionHandler = this.newStagedFunction();
		this.generateAndApplyAllObservers(functionHandler);

		if (this.defaultSaveDelegate != null) {
			functionHandler.setSaveDelegate(this.defaultSaveDelegate);
		}

		return functionHandler;
	}

	public void generateAndApplyAllObservers(F functionHandler) {
		StagedFunctionObserverMap<T, W> observerMap = this.generateObserverMap();
		this.applyAllObservers(observerMap);
		functionHandler.setObservers(observerMap);
	}

	public void applyAllObservers(F functionHandler) {
		this.applyAllObservers(functionHandler.getObservers());
	}

	public void applyAllObservers(StagedFunctionObserverMap<T, W> observerMap) {

		for (StagedFunctionObserverFactory<StagedFunctionObserver<T>, T> observerFactory : this.observerFactories) {
			StagedFunctionStagePair<StagedFunctionObserver<T>> pair = observerFactory.makeObserverStagePair();
			observerMap.add(pair.getStages(), pair.getObject());
		}

		for (StagedFunctionObjectObserverFactory<StagedFunctionObjectObserver<T, W>, T, W> observerFactory : this.objectObserverFactories)
		{
			StagedFunctionStagePair<StagedFunctionObjectObserver<T, W>> pair = observerFactory.makeObjectObserver();
			observerMap.add(pair.getStages(), pair.getObject());
		}

	}

	/**
	 * Creates a new observer map for the the function handler, and appends special observers to it.
	 * 
	 * @return
	 */
	protected final StagedFunctionObserverMap<T, W> generateObserverMap() {
		StagedFunctionObserverMap<T, W> observerMap = this.observerMapFactory.makeObserverMap();
		this.addSpecialObservers(observerMap);
		return observerMap;
	}

	/**
	 * Default implementation of {@link StagedFunctionObserverMapFactory} for creating a new observer map.
	 */
	@Override
	public StagedFunctionObserverMap<T, W> makeObserverMap() {
		StagedFunctionObserverMap<T, W> map = new StagedFunctionObserverMap<T, W>();
		return map;
	}

	/**
	 * Adds any special observers that should always go into the map for the generated.
	 */
	protected void addSpecialObservers(StagedFunctionObserverMap<T, W> observerMap) {}

	public StagedFunctionObserverMapFactory<T, W> getObserverMapFactory() {
		return this.observerMapFactory;
	}

	public void setObserverMapFactory(StagedFunctionObserverMapFactory<T, W> observerMapFactory) {
		this.observerMapFactory = observerMapFactory;
	}

	public StagedFunctionSaveDelegate<T> getDefaultSaveDelegate() {
		return this.defaultSaveDelegate;
	}

	public void setDefaultSaveDelegate(StagedFunctionSaveDelegate<T> defaultSaveDelegate) {
		this.defaultSaveDelegate = defaultSaveDelegate;
	}

	public List<StagedFunctionObserverFactory<StagedFunctionObserver<T>, T>> getObserverFactories() {
		return this.observerFactories;
	}

	public void setObserverFactories(List<StagedFunctionObserverFactory<StagedFunctionObserver<T>, T>> observerFactories) {
		this.observerFactories = observerFactories;
	}

	public List<StagedFunctionObjectObserverFactory<StagedFunctionObjectObserver<T, W>, T, W>> getObjectObserverFactories() {
		return this.objectObserverFactories;
	}

	public void setObjectObserverFactories(List<StagedFunctionObjectObserverFactory<StagedFunctionObjectObserver<T, W>, T, W>> objectObserverFactories) {
		this.objectObserverFactories = objectObserverFactories;
	}

}
