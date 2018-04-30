package com.dereekb.gae.server.app.model.hook.dto;

import com.dereekb.gae.server.app.model.app.shared.dto.AbstractAppRelatedModelData;
import com.dereekb.gae.server.app.model.hook.AppHook;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * DTO for {@link AppHook}.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppHookData extends AbstractAppRelatedModelData {

	private static final long serialVersionUID = 1L;

	private String group;
	private String event;
	private String path;

	private Boolean enabled;

	private Integer failures;

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getEvent() {
		return this.event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getFailures() {
		return this.failures;
	}

	public void setFailures(Integer failures) {
		this.failures = failures;
	}

	@Override
	public String toString() {
		return "AppHookData [group=" + this.group + ", event=" + this.event + ", path=" + this.path + ", enabled="
		        + this.enabled + ", failures=" + this.failures + ", app=" + this.app + ", key=" + this.key + ", date="
		        + this.date + "]";
	}

}
