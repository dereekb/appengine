package com.thevisitcompany.gae.deprecated.model.users.account.dto;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.KeyedModelData;

@JsonInclude(Include.NON_EMPTY)
public class AccountData extends KeyedModelData<Long> {

	private static final long serialVersionUID = 1L;

	@Size(min = 0, max = 50)
	private String name;

	private List<Long> owners;

	private List<Long> members;

	private List<Long> viewers;

	public AccountData() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Long> getOwners() {
		return owners;
	}

	public void setOwners(List<Long> owners) {
		this.owners = owners;
	}

	public List<Long> getMembers() {
		return members;
	}

	public void setMembers(List<Long> members) {
		this.members = members;
	}

	public List<Long> getViewers() {
		return viewers;
	}

	public void setViewers(List<Long> viewers) {
		this.viewers = viewers;
	}

}
