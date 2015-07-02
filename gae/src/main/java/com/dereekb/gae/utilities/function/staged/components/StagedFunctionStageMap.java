package com.dereekb.gae.utilities.function.staged.components;

import com.dereekb.gae.utilities.collections.map.HashMapWithList;

public abstract class StagedFunctionStageMap<N, O extends StagedFunctionObjectDependent> {

	private StagedFunctionStage stage = StagedFunctionStage.INITIALIZED;

	protected final HashMapWithList<StagedFunctionStage, N> normal;
	protected final HashMapWithList<StagedFunctionStage, O> objectDependent;

	public StagedFunctionStageMap() {
		this.normal = new HashMapWithList<StagedFunctionStage, N>();
		this.objectDependent = new HashMapWithList<StagedFunctionStage, O>();
	}

	public StagedFunctionStageMap(HashMapWithList<StagedFunctionStage, N> normal,
	        HashMapWithList<StagedFunctionStage, O> objectDependent) {

		if (normal != null) {
			this.normal = normal;
		} else {
			this.normal = new HashMapWithList<StagedFunctionStage, N>();
		}

		if (objectDependent != null) {
			this.objectDependent = objectDependent;
		} else {
			this.objectDependent = new HashMapWithList<StagedFunctionStage, O>();
		}
	}

	protected void clearNormal() {
		this.normal.clear();
	}

	protected void clearObjectDepdenents() {
		this.objectDependent.clear();
	}

	protected void clearAllMaps() {
		this.clearNormal();
		this.clearObjectDepdenents();
	}

	public void add(StagedFunctionStage stage,
	                N object) {
		this.normal.add(stage, object);
	}

	public void remove(StagedFunctionStage stage,
	                   N object) {
		this.normal.remove(stage, object);
	}

	public void add(StagedFunctionStage stage,
	                O object) {
		this.objectDependent.add(stage, object);
	}

	public void remove(StagedFunctionStage stage,
	                   O object) {
		this.objectDependent.remove(stage, object);
	}

	public void add(StagedFunctionStage[] stages,
	                N object) {

		for (StagedFunctionStage stage : stages) {
			this.add(stage, object);
		}
	}

	public void remove(StagedFunctionStage[] stages,
	                   N object) {
		for (StagedFunctionStage stage : stages) {
			this.remove(stage, object);
		}
	}

	public void add(StagedFunctionStage[] stages,
	                O object) {
		for (StagedFunctionStage stage : stages) {
			this.add(stage, object);
		}
	}

	public void remove(StagedFunctionStage[] stages,
	                   O object) {
		for (StagedFunctionStage stage : stages) {
			this.remove(stage, object);
		}
	}

	public final StagedFunctionStage getStage() {
		return stage;
	}

	public final void setStage(StagedFunctionStage stage) {
		this.stage = stage;
	}

	public boolean isEmpty() {
		return (this.normal.isEmpty() && this.objectDependent.isEmpty());
	}

}
