package com.kotc.db.filter.domain;

public abstract class FieldFilter implements Filter {

	private String fieldName;

	public FieldFilter() {

	}

	public FieldFilter(final String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(final String fieldName) {
		this.fieldName = fieldName;
	}
}
