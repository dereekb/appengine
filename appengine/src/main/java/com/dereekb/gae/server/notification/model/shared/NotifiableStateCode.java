package com.dereekb.gae.server.notification.model.shared;

/**
 * {@link NotifiableState} model's code. It should be unique across all states.
 *
 * @author dereekb
 *
 */
public interface NotifiableStateCode {

	/**
	 * The none state code value.
	 */
	public static final int NONE_STATE_CODE = 0;

	/**
	 * Returns the state code index.
	 *
	 * @return {@link Integer}. Never {@code null}.
	 */
	public Integer getStateCode();

}
