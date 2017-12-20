package com.kotc.db.filter.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("string")
public class StringFilter extends FieldFilter {

	private String value;

	public StringFilter() {

	}

	public StringFilter(final String fieldName, final String value) {
		super(fieldName);
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public FilterType getType() {
		return FilterType.STRING;
	}
}
