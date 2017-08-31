package com.kotc.db.filter.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("logOp")
public class LogOpFilter implements Filter {

	public static enum Operation {
		AND, OR, NOT
	}

	private Operation operation;
	private Filter[] filters;

	public LogOpFilter() {

	}

	public LogOpFilter(final Operation operation, final Filter... filters) {
		this.operation = operation;
		this.filters = filters;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(final Operation operation) {
		this.operation = operation;
	}

	public Filter[] getFilters() {
		return filters;
	}

	public void setFilters(final Filter[] filters) {
		this.filters = filters;
	}

	@Override
	public FilterType getType() {
		return FilterType.LOGICAL_OPERATION;
	}
}
