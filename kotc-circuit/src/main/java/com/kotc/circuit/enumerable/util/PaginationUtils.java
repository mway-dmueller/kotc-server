package com.kotc.circuit.enumerable.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class PaginationUtils {

	private PaginationUtils() {
		assert false : "not instantiable";
	}

	public static <T extends Serializable> Page<T> paginate(final List<T> items, final Pageable pageable) {

		final int limit = pageable.getPageSize();
		final int offset = pageable.getOffset();

		final List<T> paginated = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			if (offset > i) {
				continue;
			} else if (paginated.size() == limit) {
				break;
			}

			paginated.add(items.get(i));
		}

		return new PageImpl<>(paginated, pageable, items.size());
	}
}
