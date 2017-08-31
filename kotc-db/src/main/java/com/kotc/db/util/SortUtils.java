package com.kotc.db.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public class SortUtils {
	private static final Pattern PATTERN = Pattern.compile("([+-])([^-,]+),?");

	private SortUtils() {
		assert false : "not instantiable";
	}

	public static Sort fromString(final String sortOrder) {
		if (sortOrder == null) {
			return null;
		}

		final List<Order> orders = new ArrayList<>();

		final Matcher matcher = PATTERN.matcher(sortOrder);
		while (matcher.lookingAt()) {
			orders.add(new Order("+".equals(matcher.group(1)) ? Direction.ASC : Direction.DESC, matcher.group(2)));
			matcher.region(matcher.end(), sortOrder.length());
		}

		if (matcher.regionStart() < sortOrder.length()) {
			throw new IllegalArgumentException(sortOrder);
		}

		return new Sort(orders);
	}
}
