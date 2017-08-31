package com.kotc.db.util;

import org.springframework.data.domain.PageRequest;

import com.kotc.db.filter.util.FilterUtils;

public class QueryUtils {

	private QueryUtils() {
		assert false : "not instantiable";
	}

	public static Query getQuery(final String filterJson, final String sortOrder, final int page, final int size) {
		return Query.createBuilder()
				.filter(FilterUtils.fromJson(filterJson))
				.pageable(new PageRequest(page, size, SortUtils.fromString(sortOrder)))
				.build();
	}
}
