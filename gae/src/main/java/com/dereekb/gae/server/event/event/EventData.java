package com.dereekb.gae.server.event.event;

import com.dereekb.gae.utilities.misc.keyed.AlwaysKeyed;
import com.dereekb.gae.utilities.misc.parameters.Parameters;
import com.dereekb.gae.web.api.shared.response.ApiResponseData;

/**
 * Arbitrary data associated with an {@link Event}.
 * <p>
 * In most cases it is recommended to test and cast to a more specific event
 * data interface.
 * <p>
 * Event data can be exported (for webhooks, etc.) via
 * {{@link #getWebSafeData()}.
 *
 * @author dereekb
 *
 */
public interface EventData
        extends AlwaysKeyed<String> {

	/**
	 * Returns data type.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getEventDataType();

	/**
	 * Creates websafe data configured with default parameters.
	 *
	 * @return An object that can safely be serialized to JSON.
	 */
	public ApiResponseData getWebSafeData();

	/**
	 * Creates websafe data configured with the input parameters. If the input
	 * parameters are null, will use the default settings.
	 *
	 * @param parameters
	 *            {@link Parameters}. Can be {@code null}.
	 * @return An object that can safely be serialized to JSON.
	 */
	public ApiResponseData getWebSafeData(Parameters parameters);

	/**
	 * {@inheritDoc}
	 * <p>
	 * Returns the same value as {@link #getEventDataType()}.
	 */
	@Override
	public String keyValue();

}
