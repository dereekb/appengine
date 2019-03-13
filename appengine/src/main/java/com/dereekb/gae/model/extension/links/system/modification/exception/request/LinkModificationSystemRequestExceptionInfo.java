package com.dereekb.gae.model.extension.links.system.modification.exception.request;

import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemRequest;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * API info for a {@link LinkModificationSystemRequestException}.
 * 
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkModificationSystemRequestExceptionInfo {

	private String key;
	private String message;

	public LinkModificationSystemRequestExceptionInfo() {}
	
	public LinkModificationSystemRequestExceptionInfo(LinkModificationSystemRequest request, String message) {
		this(request.keyValue(), message);
	}

	public LinkModificationSystemRequestExceptionInfo(ModelKey key, String message) {
		this(ModelKey.readStringKey(key), message);
	}

	public LinkModificationSystemRequestExceptionInfo(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "LinkModificationSystemRequestExceptionInfo [key=" + this.key + ", message=" + this.message
		        + "]";
	}

}
