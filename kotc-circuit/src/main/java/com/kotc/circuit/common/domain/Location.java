package com.kotc.circuit.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Location implements Serializable {
	private static final long serialVersionUID = 4115629248165916775L;

	@Column(name = "city")
	private String city;

	@Column(name = "country")
	private String country;

	public Location() {
		// required for JPA
	}

	public Location(final String city, final String country) {
		this.city = city;
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}
}
