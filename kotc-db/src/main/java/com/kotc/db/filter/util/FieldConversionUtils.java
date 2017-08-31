package com.kotc.db.filter.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;

public final class FieldConversionUtils {

	/**
	 * used for logging purposes.
	 */
	// private static final Logger LOGGER = LoggerFactory.getLogger(FieldConversionUtils.class);

	/**
	 * format used for date parsing.
	 *
	 * @see #convert(Object, Class, boolean)
	 */
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat();

	/**
	 * non-constructible.
	 */
	private FieldConversionUtils() {
		assert false : "no instance";
	}

	/**
	 * converts input object of some class to specified class, with best efforts.
	 *
	 * @param src
	 *            object to convert, may be <code>null</code>.
	 * @param toClass
	 *            converted object class.
	 * @return converted object, may be <code>null</code> when <code>src</code> is
	 *         <code>null</code>.
	 *
	 * @throws IllegalArgumentException
	 *             in case conversion of src toClass is unknown.
	 * @throws RuntimeException
	 *             in case a conversion is known, but some other error with the source data prevents
	 *             conversion, e.g. parsing a string to a date failed, etc.
	 *
	 * @see #convert(Object, Class, boolean)
	 */
	public static Object convert(final Object src, final Class<?> toClass) {
		return convert(src, toClass, true);
	}

	/**
	 * converts input object of some class to specified class.
	 *
	 * <p>
	 * TODO: describe all conversion scenarios
	 * </p>
	 * <p>
	 * TODO: use apache commons converters
	 * </p>
	 *
	 * @param src
	 *            object to convert, may be <code>null</code>.
	 * @param toClass
	 *            converted object class.
	 * @param lean
	 *            when set allows highly speculative conversions, such as unwrapping collections or
	 *            arrays by taking the first element or constructing new instances and cloning bean
	 *            properties by assignment. It is recommended to set this flag to
	 *            <code>false</code>!
	 * @return converted object, may be <code>null</code> when <code>src</code> is
	 *         <code>null</code>.
	 *
	 * @throws IllegalArgumentException
	 *             in case conversion of src toClass is unknown.
	 * @throws RuntimeException
	 *             in case a conversion is known, but some other error with the source data prevents
	 *             conversion, e.g. parsing a string to a date failed, etc.
	 *
	 * @see #convert(Object, Class)
	 */
	public static Object convert(final Object src, final Class<?> toClass, final boolean lean) {
		if (src == null) {
			return null;
		}

		Object dest = null;

		if (toClass.isAssignableFrom(src.getClass())) {
			dest = src;
		} else if (toClass == Short.class || toClass == short.class) {
			if (src instanceof Number) {
				dest = ((Number) src).shortValue();
			} else if (src instanceof String) {
				dest = Short.decode((String) src);
			} else if (src instanceof Boolean) {
				dest = (short) (((Boolean) src).booleanValue() ? 1 : 0);
			}
		} else if (toClass == Integer.class || toClass == int.class) {
			if (src instanceof Number) {
				dest = ((Number) src).intValue();
			} else if (src instanceof String) {
				dest = Integer.decode((String) src);
			} else if (src instanceof Character) {
				dest = (int) ((Character) src).charValue();
			} else if (src instanceof Boolean) {
				dest = ((Boolean) src).booleanValue() ? 1 : 0;
			}
		} else if (toClass == Long.class || toClass == long.class) {
			if (src instanceof Number) {
				dest = ((Number) src).longValue();
			} else if (src instanceof String) {
				dest = Long.decode((String) src);
			} else if (src instanceof Date) {
				dest = ((Date) src).getTime();
			} else if (src instanceof Character) {
				dest = (long) ((Character) src).charValue();
			} else if (src instanceof Boolean) {
				dest = (long) (((Boolean) src).booleanValue() ? 1 : 0);
			}
		} else if (toClass == Float.class || toClass == float.class) {
			if (src instanceof Number) {
				dest = ((Number) src).floatValue();
			} else if (src instanceof String) {
				dest = Float.valueOf((String) src);
			}
		} else if (toClass == Double.class || toClass == double.class) {
			if (src instanceof Number) {
				dest = ((Number) src).doubleValue();
			} else if (src instanceof String) {
				dest = Double.valueOf((String) src);
			}
		} else if (toClass == Number.class) {
			if (src instanceof String) {
				dest = NumberUtils.createNumber((String) src);
			} else if (src instanceof Character) {
				dest = (int) ((Character) src).charValue();
			} else if (src instanceof Boolean) {
				dest = ((Boolean) src).booleanValue() ? 1 : 0;
			}
		} else if (toClass == BigInteger.class) {
			if (lean) {
				// allow various writings: 3, 3.0, ... and drop any fractional part
				dest = NumberUtils.createBigDecimal(src.toString()).toBigInteger();
			} else if (src instanceof String) {
				// strict and exactly as written, no fraction permitted
				dest = NumberUtils.createBigInteger((String) src);
			} else if (src instanceof Number) {
				// strict, but an explicit fraction of 0 is allowed, such as 3.0 for Double
				dest = NumberUtils.createBigDecimal(src.toString()).toBigIntegerExact();
			}
		} else if (toClass == BigDecimal.class) {
			if (src instanceof String) {
				dest = NumberUtils.createBigDecimal((String) src);
			} else if (lean || src instanceof Number) {
				dest = NumberUtils.createBigDecimal(src.toString());
			}
		} else if (toClass == Boolean.class || toClass == boolean.class) {
			if (src instanceof Boolean) { // for Boolean -> boolean
				dest = src;
			} else if (src instanceof String) {
				if (((String) src).equalsIgnoreCase("true") || ((String) src).equalsIgnoreCase("yes")
						|| ((String) src).equalsIgnoreCase("on")
						|| ((String) src).equals("1")) {
					dest = Boolean.TRUE;
				} else if (((String) src).equalsIgnoreCase("false") || ((String) src).equalsIgnoreCase("no")
						|| ((String) src).equalsIgnoreCase("off")
						|| ((String) src).equals("0")) {
					dest = Boolean.FALSE;
				} else if (((String) src).isEmpty() || ((String) src).equalsIgnoreCase("null")
						|| ((String) src).equalsIgnoreCase("unknown")
						|| ((String) src).equalsIgnoreCase("undefined")) {
					return null; // valid result
				}
			} else if (src instanceof Character) {
				final char c = ((Character) src).charValue();
				if (c == ' ') {
					return null; // valid result
				} else if (c == '0' || c == 'n' || c == 'N') {
					dest = Boolean.FALSE;
				} else if (c == '1' || c == 'y' || c == 'N') {
					dest = Boolean.TRUE;
				}
			} else if (src instanceof Short || src instanceof Integer || src instanceof Long || src instanceof Byte) {
				// above does not accept floating point types
				dest = ((Number) src).longValue() != 0;
			}
		} else if (toClass == Character.class || toClass == char.class) {
			if (src instanceof Number) {
				dest = (char) ((Number) src).intValue();
			}

		} else if (toClass == InputStream.class) {
			if (src instanceof String) {
				dest = new ByteArrayInputStream(((String) src).getBytes());
			} else if (src instanceof byte[]) {
				dest = new ByteArrayInputStream((byte[]) src);
			}
		} else if (toClass == Reader.class) {
			if (src instanceof String) {
				dest = new StringReader((String) src);
			} else if (src instanceof byte[]) {
				dest = new InputStreamReader(new ByteArrayInputStream((byte[]) src));
			} else if (src instanceof char[]) {
				dest = new CharArrayReader((char[]) src);
			}
		} else if (toClass == Date.class) {
			dest = toDate(src, lean);
		} else if (toClass == Calendar.class) {
			final Date date = toDate(src, lean);
			if (date != null) {
				dest = Calendar.getInstance();
				((Calendar) dest).setTime(date);
			}
		} else if (toClass == String.class) {
			if (src instanceof Reader) {
				try {
					dest = IOUtils.toString((Reader) src);
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			} else if (src instanceof InputStream) {
				try {
					dest = IOUtils.toString((InputStream) src, "UTF-8");
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			} else if (src instanceof byte[]) {
				dest = new String((byte[]) src);
			} else if (src instanceof char[]) {
				dest = new String((char[]) src);
			} else if (src instanceof File) {
				// reading the File to String alternative
				// try {
				// IOUtils.toString(new FileInputStream((String) src));
				// } catch (IOException e) {
				// throw new RuntimeException(e);
				// }
				dest = ((File) src).getAbsolutePath();
			} else {
				dest = src.toString();
			}
		} else if (toClass == byte[].class) {
			if (src instanceof String) {
				dest = ((String) src).getBytes();
			} else if (src instanceof InputStream) {
				try {
					dest = IOUtils.toByteArray((InputStream) src);
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			} else if (src instanceof Reader) {
				try {
					dest = IOUtils.toByteArray((Reader) src, "UTF-8");
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
		} else if (toClass == char[].class) {
			if (src instanceof String) {
				dest = ((String) src).toCharArray();
			} else if (src instanceof InputStream) {
				try {
					dest = IOUtils.toCharArray((InputStream) src, "UTF-8");
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			} else if (src instanceof Reader) {
				try {
					dest = IOUtils.toCharArray((Reader) src);
				} catch (final IOException e) {
					throw new RuntimeException(e);
				}
			}
		} else if (toClass == URI.class) {
			if (src instanceof String) {
				dest = URI.create((String) src);
			} else if (src instanceof File) {
				dest = ((File) src).toURI();
			} else if (src instanceof URL) {
				try {
					dest = ((URL) src).toURI();
				} catch (final URISyntaxException e) {
					throw new RuntimeException(e);
				}
			}
		} else if (toClass == URL.class) {
			try {
				if (src instanceof String) {
					dest = URI.create((String) src).toURL();
				} else if (src instanceof File) {
					dest = ((File) src).toURI().toURL();
				} else if (src instanceof URI) {
					dest = ((URI) src).toURL();
				}
			} catch (final MalformedURLException e) {
				throw new RuntimeException(e);
			}
		} else if (File.class == toClass) {
			if (src instanceof String) {
				dest = new File((String) src);
			}
		} else if (toClass.isArray()) {
			final Class<?> componentType = toClass.getComponentType();
			if (src instanceof Collection<?>) {
				final Collection<?> collection = (Collection<?>) src;
				dest = Array.newInstance(componentType, collection.size());
				int i = 0;
				for (final Object item : collection) {
					Array.set(dest, i++, convert(item, componentType));
				}
			} else if (src instanceof Iterable<?>) {
				final List<Object> list = new ArrayList<>();
				for (final Object name : ((Iterable<?>) src)) {
					list.add(name);
				}

				dest = Array.newInstance(componentType, list.size());
				int i = 0;
				for (final Object item : list) {
					Array.set(dest, i++, convert(item, componentType));
				}
			} else if (src instanceof Object[]) {
				// convert each item in array of one type to items in array of another type
				dest = Array.newInstance(componentType, Array.getLength(src));
				int i = 0;
				for (final Object item : (Object[]) src) {
					Array.set(dest, i++, convert(item, componentType));
				}
				// } else if (src instanceof String) {
				// final Class<?> componentType = toClass.getComponentType();
				// final String[] items = ((String) src).split("\\s*,\\s*");
				// dest = Array.newInstance(componentType, items.length);
				//
				// int i = 0;
				// for (final String item : items) {
				// Array.set(dest, i++, convert(item, componentType));
				// }
			} else {
				dest = Array.newInstance(componentType, 1);
				Array.set(dest, 0, convert(src, componentType));
			}
		} else if (Collection.class.isAssignableFrom(toClass) || Iterable.class.isAssignableFrom(toClass)) {
			dest = convertCollection(src, toClass);
		} else if (Enum.class.isAssignableFrom(toClass)) {
			dest = toEnum(src, toClass);
		} else if (lean && src instanceof Object[]) {
			// lean only as this is highly speculative!
			final Object[] array = (Object[]) src;
			dest = array.length == 0 ? null : convert(array[0], toClass);
		} else if (lean && src instanceof Collection) {
			// lean only as this is highly speculative!
			final Iterator<?> iter = ((Collection<?>) src).iterator();
			dest = iter.hasNext() ? convert(iter.next(), toClass) : null;
		} else {
			Object object = null;
			final boolean isIface = toClass.isInterface() || Modifier.isAbstract(toClass.getModifiers());

			if (isIface && toClass.isAssignableFrom(TreeMap.class)) {
				object = new TreeMap<>();
			} else if (isIface && toClass.isAssignableFrom(LinkedHashMap.class)) {
				object = new LinkedHashMap<>();
			} else if (lean && !isIface) {
				// lean only as this is highly speculative!
				try {
					object = toClass.newInstance();
				} catch (final InstantiationException | IllegalAccessException e) {
					// LOGGER.debug("instantiation failed", e);
				}
			}

			if (object != null) {
				try {
					for (final PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(toClass)) {
						PropertyUtils.setProperty(object, pd.getName(),
								convert(PropertyUtils.getProperty(src, pd.getName()), pd.getPropertyType()));
					}
					dest = object;
				} catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException
						| IllegalArgumentException e) {
					// LOGGER.debug("property assignment failed", e);
					assert dest == null;
				}
			}
		}

		if (dest == null) {
			throw new IllegalArgumentException("can't convert object of class " + src.getClass().getName()
					+ " to class " + toClass.getName() + " for value "
					+ src);
		}

		return dest;
	}

	/**
	 * conversion to {@link Date}.
	 *
	 * @param src
	 *            value.
	 * @param lean
	 *            whether to relax, i.e. accept unknown src types by attempting to parse
	 *            {@link Object#toString()} representation.
	 * @return {@link Date} value, or <code>null</code>.
	 */
	private static Date toDate(final Object src, final boolean lean) {
		if (src instanceof Calendar) {
			return ((Calendar) src).getTime();
		} else if (src instanceof Date) {
			return (Date) src;
		} else if (src instanceof Number) {
			return new Date(((Number) src).longValue());
		} else if (lean ? src != null : src instanceof String) {
			try {
				return SIMPLE_DATE_FORMAT.parse(src.toString());
			} catch (final ParseException e) {
				// TODO fallback date parsing
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Enum<? extends Enum<?>> toEnum(final Object src, final Class<?> toClass) {
		return Enum.valueOf((Class<Enum>) toClass, src.toString());
	}

	@SuppressWarnings("unchecked")
	private static <T> Collection<Object> convertCollection(final Object src, final Class<T> toClass) {
		final Collection<Object> dest;

		// TODO
		// final TypeVariable<Class<T>> typeVariable = toClass.getTypeParameters()[0];

		final boolean isIface = toClass.isInterface() || Modifier.isAbstract(toClass.getModifiers());

		if (isIface && toClass.isAssignableFrom(ArrayList.class)) {
			dest = new ArrayList<>();
		} else if (isIface && toClass.isAssignableFrom(HashSet.class)) {
			dest = new HashSet<>();
		} else if (isIface && toClass.isAssignableFrom(ArrayDeque.class)) {
			dest = new ArrayDeque<>();
		} else if (isIface) {
			throw new IllegalArgumentException();
		} else {
			try {
				dest = (Collection<Object>) toClass.newInstance();
			} catch (final InstantiationException e) {
				throw new IllegalArgumentException();
			} catch (final IllegalAccessException e) {
				throw new IllegalArgumentException();
			}
		}

		if (src instanceof Object[]) {
			for (final Object item : (Object[]) src) {
				dest.add(item);
			}
		} else if (src instanceof Collection) {
			dest.addAll((Collection<?>) src);
		} else {
			dest.add(src);
		}

		return dest;
	}
}
