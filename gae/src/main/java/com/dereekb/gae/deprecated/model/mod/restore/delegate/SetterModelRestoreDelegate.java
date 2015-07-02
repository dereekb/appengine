package com.thevisitcompany.gae.deprecated.model.mod.restore.delegate;

import java.util.Collection;

import com.thevisitcompany.gae.deprecated.model.mod.restore.ModelRestoreDelegate;
import com.thevisitcompany.gae.server.datastore.Setter;

@Deprecated
public class SetterModelRestoreDelegate<T>
        implements ModelRestoreDelegate<T> {

	private Setter<T> setter;
	private Boolean async = true;

	public SetterModelRestoreDelegate(Setter<T> setter) {
		super();
		this.setter = setter;
	}

	@Override
	public void restore(Collection<T> objects) {
		this.setter.save(objects, this.async);
	}

	public Setter<T> getSetter() {
		return this.setter;
	}

	public void setSetter(Setter<T> setter) throws IllegalArgumentException {
		if (setter == null) {
			throw new IllegalArgumentException("Setter for ModelRestorer cannot be null.");
		}

		this.setter = setter;
	}

	public Boolean getAsync() {
		return this.async;
	}

	public void setAsync(Boolean async) {
		this.async = async;
	}
}
