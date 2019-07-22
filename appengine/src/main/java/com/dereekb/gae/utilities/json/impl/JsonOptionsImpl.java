package com.dereekb.gae.utilities.json.impl;

import com.dereekb.gae.utilities.json.JsonOptions;

/**
 * {@link JsonOptions} implementation.
 *
 * @author dereekb
 *
 */
public class JsonOptionsImpl
        implements JsonOptions {

	private Boolean prettyPrinting = false;
	private Boolean serializeNulls = false;

	public JsonOptionsImpl() {}

	@Override
	public Boolean getPrettyPrinting() {
		return this.prettyPrinting;
	}

	public void setPrettyPrinting(Boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
	}

	@Override
	public Boolean getSerializeNulls() {
		return this.serializeNulls;
	}

	public void setSerializeNulls(Boolean serializeNulls) {
		this.serializeNulls = serializeNulls;
	}

	@Override
	public String toString() {
		return "JsonOptionsImpl [prettyPrinting=" + this.prettyPrinting + ", serializeNulls=" + this.serializeNulls
		        + "]";
	}

}
