package com.thevisitcompany.gae.deprecated.model.users.user.dto;

import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thevisitcompany.gae.deprecated.model.mod.data.conversion.KeyedModelData;

@JsonInclude(Include.NON_EMPTY)
public class UserData extends KeyedModelData<Long> {

	private static final long serialVersionUID = 1L;

	@Size(min = 0, max = 50)
	private String name;

	@Size(min = 0, max = 50)
	private String company;

	@Size(min = 9, max = 12)
	private String phonenumber;

	private List<Long> destinations;

	private List<Long> series;

	private List<Long> places;

	public UserData() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public List<Long> getDestinations() {
		return destinations;
	}

	public void setDestinations(List<Long> destinations) {
		this.destinations = destinations;
	}

	public List<Long> getSeries() {
		return series;
	}

	public void setSeries(List<Long> series) {
		this.series = series;
	}

	public List<Long> getPlaces() {
		return places;
	}

	public void setPlaces(List<Long> places) {
		this.places = places;
	}

}
