package com.thevisitcompany.gae.deprecated.model.mod.restore;

import java.util.Collection;
import java.util.List;

import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.ModelConverter;

/**
 * Special ModelRestorer that uses a DataManager to convert between types then restores the converted objects.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <A>
 */
public class DataModelRestorer<T, A> extends ModelRestorer<T> {

	protected ModelConverter<T, A> converter;

	public DataModelRestorer() {
		super();
	}

	public DataModelRestorer(ModelConverter<T, A> converter) {
		super();
		this.converter = converter;
	}

	public DataModelRestorer(ModelRestoreDelegate<T> delegate, ModelConverter<T, A> converter) {
		super(delegate);
		this.converter = converter;
	}

	public void restoreData(A data) {
		T object = this.converter.convertToObject(data);
		this.restore(object);
	}

	public void restoreData(Collection<A> data) {
		List<T> objects = this.converter.convertToObjects(data);
		this.restore(objects);
	}

	public ModelConverter<T, A> getConverter() {
		return this.converter;
	}

	public void setConverter(ModelConverter<T, A> converter) {
		this.converter = converter;
	}

}
