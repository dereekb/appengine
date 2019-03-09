package com.dereekb.gae.model.extension.links.components.exception;

import com.dereekb.gae.model.extension.links.components.Relation;
import com.dereekb.gae.model.extension.links.exception.ApiLinkException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.api.shared.response.ApiResponseError;
import com.dereekb.gae.web.api.shared.response.impl.ApiResponseErrorImpl;

/**
 * Exception that occurs when a relation change experiences an error.
 *
 * @author dereekb
 *
 */
public class RelationChangeException extends ApiLinkException {

	private static final long serialVersionUID = 1L;

	public static final String ERROR_CODE = "CHANGE_FAILURE";
	public static final String ERROR_TITLE = "Link Change Failure";

	private final ModelKey primary;
	private final Relation relation;

	public RelationChangeException(ModelKey primary, Relation relation, Throwable cause) {
		super(cause);
		this.primary = primary;
		this.relation = relation;
	}

	public RelationChangeException(ModelKey primary, Relation relation, String message) {
		super(message);
		this.primary = primary;
		this.relation = relation;
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
		return "RelationChangeException [primary=" + this.primary + ", relation=" + this.relation + ", getMessage()="
		        + this.getMessage() + "]";
	}

}
