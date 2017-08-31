package com.kotc.circuit.enumerable.domain;

import java.io.Serializable;

public class KeyValuePair implements Serializable {
	private static final long serialVersionUID = -8817929723146660304L;

	private String key;
	private String value;

	public KeyValuePair(final String value) {
		key = value;
		this.value = value;
	}

	public KeyValuePair(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
