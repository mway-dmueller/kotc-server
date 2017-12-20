package com.kotc.db.util;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonSerializationUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private JsonSerializationUtils() {
		assert false : "not instantiable";
	}

	public static <T> String serialize(final T object) {
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (final IOException e) {
			throw new IllegalArgumentException("invalid object", e);
		}
	}

	public static <T> T deserialize(final Class<T> clazz, final String json) {
		try {
			return OBJECT_MAPPER.readValue(json, clazz);
		} catch (final IOException e) {
			throw new IllegalArgumentException("invalid json", e);
		}
	}
}
