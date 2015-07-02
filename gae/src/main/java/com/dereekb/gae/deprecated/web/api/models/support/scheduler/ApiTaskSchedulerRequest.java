package com.thevisitcompany.gae.deprecated.web.api.models.support.scheduler;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * Wrapper for {@link ApiTaskScheduler} request information.
 * 
 * @author dereekb
 */
public class ApiTaskSchedulerRequest {

	@NotNull
	private String name;

	private Map<String, String> info;

	private List<Long> identifiers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getInfo() {
		return info;
	}

	public void setInfo(Map<String, String> info) {
		this.info = info;
	}

	public List<Long> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Long> identifiers) {
		this.identifiers = identifiers;
	}

}
