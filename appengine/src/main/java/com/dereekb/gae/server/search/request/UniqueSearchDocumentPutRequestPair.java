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
public class UniqueSearchDocumentPutRequestPair extends SearchServiceIndexRequestPair
        implements UniqueModel {

	private ModelKey modelKey;

	public UniqueSearchDocumentPutRequestPair(Keyed<ModelKey> model, Document source) {
		this(model.keyValue(), source);
	}

	public UniqueSearchDocumentPutRequestPair(ModelKey modelKey, Document source) {
		super(source);
		this.setModelKey(modelKey);
	}

	@Override
	public ModelKey getModelKey() {
		return this.modelKey;
	}

	public void setModelKey(ModelKey modelKey) {
		if (modelKey == null) {
			throw new IllegalArgumentException("modelKey cannot be null.");
		}

		this.modelKey = modelKey;
	}

	@Override
	public ModelKey keyValue() {
		return this.getModelKey();
	}

}
