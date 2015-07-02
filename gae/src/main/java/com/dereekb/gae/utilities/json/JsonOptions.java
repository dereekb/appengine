package com.dereekb.gae.utilities.json;

public class JsonOptions {

	private Boolean prettyPrinting = false;
	private Boolean serializeNulls = false;
	
	public JsonOptions(){}

	public Boolean getPrettyPrinting() {
		return prettyPrinting;
	}

	public void setPrettyPrinting(Boolean prettyPrinting) {
		this.prettyPrinting = prettyPrinting;
	}

	public Boolean getSerializeNulls() {
		return serializeNulls;
	}

	public void setSerializeNulls(Boolean serializeNulls) {
		this.serializeNulls = serializeNulls;
	}
	
}

