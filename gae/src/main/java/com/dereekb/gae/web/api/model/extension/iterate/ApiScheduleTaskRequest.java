package com.dereekb.gae.web.api.model.extension.iterate;

import java.util.Map;

/**
 * Request for {@link ApiScheduleTaskController}.
 * 
 * @author dereekb
 *
 */
public interface ApiScheduleTaskRequest {

	public String getTaskName();
	
	public Map<String, String> getEncodedHeaders();
	
	public Map<String, String> getEncodedParameters();
	
}
