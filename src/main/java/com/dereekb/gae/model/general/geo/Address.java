package com.dereekb.gae.model.general.geo;

/**
 * Represents a detailed postal address for a point on the map.
 *
 * @author dereekb
 */
public class Address {

	/**
	 * Address's name.
	 */
	private String name;

	/**
	 * The street address to display.
	 */
	private String street;

	/**
	 * The area/district.
	 */
	private String area;

	/**
	 * Current city
	 */
	private String city;

	/**
	 * Postal/Area code.
	 */
	private String postal;

	public Address() {}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal() {
		return this.postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	@Override
	public String toString() {
		return "Address [name=" + this.name + ", street=" + this.street + ", area=" + this.area + ", city=" + this.city
		        + ", postal=" + this.postal + "]";
	}

}
