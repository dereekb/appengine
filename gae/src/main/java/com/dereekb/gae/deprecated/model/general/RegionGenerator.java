package com.thevisitcompany.gae.deprecated.model.general;

import com.thevisitcompany.gae.model.extension.generation.AbstractGenerator;

public class RegionGenerator extends AbstractGenerator<WorldRegion> {

	private String state = "Texas";
	private String country = "United States";

	@Override
	public WorldRegion generate() {
		WorldRegion region = new WorldRegion();

		region.setCountry(country);
		region.setRegion(state);

		return region;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}