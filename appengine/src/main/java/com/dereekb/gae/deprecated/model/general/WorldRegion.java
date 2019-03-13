package com.thevisitcompany.gae.deprecated.model.general;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Contains human regional information, such as region, county, etc.
 *
 * @deprecated Is replaced by better-formatted information types.
 */
@Deprecated
public class WorldRegion {

	@Size(min = 0, max = 50)
	private String region;

	@Size(min = 0, max = 50)
	private String country;

	public WorldRegion() {}

	public WorldRegion(String region, String country) {
		this.region = region;
		this.country = country;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@JsonIgnore
	public boolean isDefault() {
		return (this.region == null && this.country == null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.country == null) ? 0 : this.country.hashCode());
		result = prime * result + ((this.region == null) ? 0 : this.region.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
	        return true;
        }
		if (obj == null) {
	        return false;
        }
		if (this.getClass() != obj.getClass()) {
	        return false;
        }
		WorldRegion other = (WorldRegion) obj;
		if (this.country == null) {
			if (other.country != null) {
	            return false;
            }
		} else if (!this.country.equals(other.country)) {
	        return false;
        }
		if (this.region == null) {
			if (other.region != null) {
	            return false;
            }
		} else if (!this.region.equals(other.region)) {
	        return false;
        }
		return true;
	}

	@Override
	public String toString() {
		return "Region [region=" + this.region + ", country=" + this.country + "]";
	}

}
