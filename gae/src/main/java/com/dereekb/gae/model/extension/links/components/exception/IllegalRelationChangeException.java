package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * {@link RelationChangeException} thrown when an illegal change is attempted.
 * 
 * @author dereekb
 *
 */
public class IllegalRelationChangeException extends RelationChangeException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "ILLEGAL_CHANGE";
	public static final String ERROR_TITLE = "Illegal Link Change";

	public IllegalRelationChangeException() {
		this(null, null);
	}

	public IllegalRelationChangeException(Relation relation) {
		this(null, relation);
	}

	public IllegalRelationChangeException(ModelKey primary) {
		this(primary, null);
	}

	public IllegalRelationChangeException(ModelKey primary, Relation relation) {
		super(primary, relation, "Changing this link is forbidden.");
	}

	public IllegalRelationChangeException(ModelKey primary, Relation relation, String message) {
		super(primary, relation, message);
	}

	public IllegalRelationChangeException(ModelKey primary, Relation relation, Throwable cause) {
		super(primary, relation, cause);
	}

	// MARK: ApiResponseErrorConvertable
	@Override
	public ApiResponseError asResponseError() {
		ApiResponseErrorImpl error = new ApiResponseErrorImpl(ERROR_CODE, ERROR_TITLE);
		error.setDetail(this.getMessage());
		return error;
	}

	@Override
	public String toString() {
		return "IllegalRelationChangeException []";
	}

}
