package com.thevisitcompany.gae.deprecated.web.api.models.base.cruds.create;

import java.util.List;

import com.thevisitcompany.gae.deprecated.web.api.ApiRequest;

/**
 * Request for creating elements.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class CreateRequest<T, K> extends ApiRequest<T>> {

	/**
	 * Type of this create request. Used for differentiating.
	 */
	private String type;

	/**
	 * Parent Identifier
	 */
	private K parent;

	/**
	 * Parent Type
	 */
	private String parentType;

	/**
	 * Alternative to data in {@link ApiRequest}. Can just specify names of
	 * elements.
	 */
	private List<String> names;

	public CreateRequest() {}

	public <A> CreateRequest(CreateRequest<A, K> request) {
		this.type = request.type;
		this.parent = request.parent;
		this.parentType = request.parentType;
		this.names = request.names;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public K getParent() {
		return this.parent;
	}

	public void setParent(K parent) {
		this.parent = parent;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public List<String> getNames() {
		return this.names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
