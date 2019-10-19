package com.dereekb.gae.server.search.request;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.Keyed;
import com.google.appengine.api.search.Document;

/**
 * {@link SearchServiceIndexRequestPair} extension that implements
 * {@link UniqueModel} to allow associating it with a specific model.
 *
 * @author dereekb
 *
 */
public class KeyedSearchDocumentPutRequestPair<T extends Keyed<ModelKey>> extends SearchServiceIndexRequestPair
        implements UniqueModel {

	private Keyed<ModelKey> keyedModel;

	public KeyedSearchDocumentPutRequestPair(Keyed<ModelKey> model, Document source) {
		super(source);
		this.setKeyedModel(model);
	}

	public Keyed<ModelKey> getKeyedModel() {
		return this.keyedModel;
	}

	public void setKeyedModel(Keyed<ModelKey> keyedModel) {
		if (keyedModel == null) {
			throw new IllegalArgumentException("keyedModel cannot be null.");
		}

		this.keyedModel = keyedModel;
	}

	// MARK: UniqueModel
	@Override
	public ModelKey getModelKey() {
		return this.keyedModel.keyValue();
	}

	@Override
	public ModelKey keyValue() {
		return this.getModelKey();
	}

}
