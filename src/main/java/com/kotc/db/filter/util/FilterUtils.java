package com.kotc.db.filter.util;

import com.kotc.db.filter.domain.Filter;
import com.kotc.db.util.JsonSerializationUtils;

public class FilterUtils {

	public static Filter fromJson(final String filterJson) {
		if (filterJson == null) {
			return null;
		}

		return JsonSerializationUtils.deserialize(Filter.class, filterJson);
	}
}
