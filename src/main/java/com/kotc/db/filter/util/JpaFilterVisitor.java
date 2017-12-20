package com.kotc.db.filter.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type.PersistenceType;

import com.kotc.db.filter.domain.Filter;
import com.kotc.db.filter.domain.LogOpFilter;
import com.kotc.db.filter.domain.StringFilter;

public class JpaFilterVisitor<T, U> implements FilterVisitor {

	public static final String LEFT_JOIN = "+";
	public static final String ALIAS = "::";

	/**
	 * * prefix character of a join for each {@link JoinType} in order of * enumeration. * *
	 * <p>
	 * * '\0' for undefined values. *
	 * </p>
	 */
	private static final String JOINS = "\0" + LEFT_JOIN + "\0";

	private final Path<T> root;
	private final CriteriaQuery<U> criteriaQuery;
	private final CriteriaBuilder criteriaBuilder;

	private final Map<String, Path<?>> paths;

	private Predicate criteria;

	public JpaFilterVisitor(final Path<T> root, final CriteriaQuery<U> criteriaQuery,
			final CriteriaBuilder criteriaBuilder) {
		this.root = root;
		this.criteriaQuery = criteriaQuery;
		this.criteriaBuilder = criteriaBuilder;
		this.paths = new HashMap<>();
	}

	public JpaFilterVisitor(final Path<T> root, final CriteriaQuery<U> criteriaQuery,
			final CriteriaBuilder criteriaBuilder, final Map<String, Path<?>> paths) {
		this.root = root;
		this.criteriaQuery = criteriaQuery;
		this.criteriaBuilder = criteriaBuilder;
		this.paths = paths;
	}

	@Override
	public void visit(final Filter filter) {

		switch (filter.getType()) {
		case LOGICAL_OPERATION:
			visit((LogOpFilter) filter);
			break;
		case STRING:
			visit((StringFilter) filter);
			break;
		default:
			throw new UnsupportedOperationException(filter.getType().toString());
		}
	}

	private void visit(final LogOpFilter filter) {
		final CriteriaBuilder cb = getCriteriaBuilder();
		final Filter[] filters = filter.getFilters();
		final Predicate[] predicates = new Predicate[filters.length];
		for (int i = 0; i < filters.length; ++i) {
			final JpaFilterVisitor<T, U> jpaFilterVisitor = new JpaFilterVisitor<>(getRoot(), getCriteriaQuery(),
					getCriteriaBuilder(), getPaths());
			jpaFilterVisitor.visit(filters[i]);
			predicates[i] = jpaFilterVisitor.getCriteria();
		}

		switch (filter.getOperation()) {
		case AND:
			setCriteria(cb.and(predicates));
			break;
		case OR:
			setCriteria(cb.or(predicates));
			break;
		case NOT:
			setCriteria(cb.not(predicates[0]));
		default:
			throw new UnsupportedOperationException(filter.getOperation().toString());
		}
	}

	private void visit(final StringFilter filter) {

	}

	/**
	 * * * alias is a suffix of attribute name separated by :: for instance name::A * outer join is
	 * a prefix + of attribute name for instance +name::A * all is a star * used mostly in count
	 * function * * @return the path
	 */
	public Path<?> getPath(final String fieldName) {
		final Path<?> cached = paths.get(fieldName);
		if (cached != null) {
			return cached;
		}
		final int separator = fieldName.lastIndexOf('.');
		final Path<?> from = separator < 0 ? root : getPath(fieldName.substring(0, separator));
		final String name = fieldName.substring(separator + 1);
		final Path<?> path;
		if ("*".equals(name)) {
			// just a match any
			path = from;
		} else if (from instanceof MapJoin && ((MapJoin<?, ?, ?>) from).getOn() == null
				&& ((PluralAttribute<?, ?, ?>) ((MapJoin<?, ?, ?>) from).getAttribute()).getElementType()
						.getPersistenceType() == PersistenceType.BASIC) {
			// interpret name as a map key of basic element collection to navigate
			final MapJoin<?, ?, ?> mapJoin = (MapJoin<?, ?, ?>) from;
			final Path<?> mapKey = mapJoin.key();
			path = mapJoin
					.on(criteriaBuilder.equal(mapKey, FieldConversionUtils.convert(name, mapKey.getJavaType(), false)))
					.value();
		} else {
			// attribute is substring between join and alias, i.e. +attribute::alias
			final int joinOrdinal = JOINS.indexOf(name.charAt(0));
			final int joinIndex = joinOrdinal >= 0 ? 1 : 0;
			final int aliasIndex = name.indexOf(ALIAS, joinIndex);
			final String attribute = name.substring(joinIndex, aliasIndex < 0 ? name.length() : aliasIndex);
			if (joinOrdinal < 0) {
				// plain attribute
				path = from.get(attribute);
			} else {
				// join found
				assert JoinType.values().length == JOINS.length();
				final JoinType joinType = JoinType.values()[joinOrdinal];
				path = ((From<?, ?>) from).join(attribute, joinType);
			}
		}
		paths.put(fieldName, path);
		return path;
	}

	/**
	 * computes a JPA {@link Order} from a {@link org.springframework.data.domain.Sort.Order}.
	 *
	 * @param order
	 *            to build {@link Order} from.
	 * @return an {@link Order} by element.
	 */
	public Order getOrder(final org.springframework.data.domain.Sort.Order order) {
		Expression<?> path = getPath(order.getProperty());
		if (path instanceof MapJoin) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=453865 with incorrect fix
			// https://github.com/eclipse/eclipselink.runtime/commit/653b5dff420f2e27b21fffbbe37978be3369a57c
			// COALESCE() of single argument is NOT allowed by SQL Server
			path = criteriaBuilder.coalesce(path, criteriaBuilder.nullLiteral(path.getJavaType()));
		}
		return order.isAscending() ? criteriaBuilder.asc(path) : criteriaBuilder.desc(path);
	}

	public Path<T> getRoot() {
		return root;
	}

	public CriteriaQuery<U> getCriteriaQuery() {
		return criteriaQuery;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return criteriaBuilder;
	}

	public Map<String, Path<?>> getPaths() {
		return paths;
	}

	public Predicate getCriteria() {
		return criteria;
	}

	public void setCriteria(final Predicate criteria) {
		this.criteria = criteria;
	}
}
