package com.dereekb.gae.web.api.util.attribute.impl;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.util.attribute.AttributeUpdateFailure;
import com.dereekb.gae.web.api.util.attribute.KeyedInvalidAttribute;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Data transfer object for a {@link AttributeUpdateFailure} and
 * {@link KeyedInvalidAttribute}.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class KeyedInvalidAttributeData extends InvalidAttributeImpl {

	private String key;

	public KeyedInvalidAttributeData(AttributeUpdateFailure failure) {
		super(failure);
	}

	public KeyedInvalidAttributeData(KeyedInvalidAttribute failure) {
		super(failure);
		this.setKey(failure.keyValue());
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setKey(UniqueModel uniqueModel) {
		String key = null;
		ModelKey modelKey = uniqueModel.getModelKey();

		if (modelKey != null) {
			this.key = modelKey.toString();
		}

		this.setKey(key);
	}

}
