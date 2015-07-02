package com.thevisitcompany.gae.deprecated.web.response.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thevisitcompany.gae.deprecated.web.response.ApiResponse;

/**
 * Used for responses that may send back a list of modified identifiers.
 *
 * @author dereekb
 */
public class ApiIdentifierResponse<K> extends ApiResponse {

	private static final String IDENTIFIERS_KEY = "identifiers";

	public ApiIdentifierResponse() {}

	public ApiIdentifierResponse(K identifier) {
		this.addIdentifier(identifier);
	}

	public ApiIdentifierResponse(List<K> identifiers) {
		this.setIdentifiers(identifiers);
	}

	@JsonIgnore
	@SuppressWarnings("unchecked")
	public List<K> getIdentifiers() {
		Object idsObject = this.readInfo(IDENTIFIERS_KEY);
		List<K> modifiedIds = null;

		if (idsObject != null) {
			modifiedIds = (List<K>) idsObject;
		}

		return modifiedIds;
	}

	@JsonIgnore
	public List<K> getOrCreateIdentifiers() {
		List<K> modifiedIds = this.getIdentifiers();

		if (modifiedIds == null) {
			modifiedIds = new ArrayList<K>();
			this.setIdentifiers(modifiedIds);
		}

		return modifiedIds;
	}

	@JsonIgnore
	public void setIdentifiers(Collection<K> identifiers) {
		List<K> idsArray = new ArrayList<K>(identifiers);
		this.putInfo(IDENTIFIERS_KEY, idsArray);
	}

	@JsonIgnore
	public void setIdentifier(K identifier) {
		List<K> idsArray = new ArrayList<K>();
		idsArray.add(identifier);
		this.setIdentifiers(idsArray);
	}

	@JsonIgnore
	public void addIdentifier(K identifier) {
		List<K> identifiers = this.getOrCreateIdentifiers();
		identifiers.add(identifier);
	}

	@JsonIgnore
	public boolean hasIdentifiersInfo() {
		return this.hasInfo(IDENTIFIERS_KEY);
	}

}
