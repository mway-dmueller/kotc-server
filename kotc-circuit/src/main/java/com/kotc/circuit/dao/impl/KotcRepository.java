package com.kotc.circuit.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.kotc.db.filter.domain.Filter;
import com.kotc.db.filter.util.JpaFilterVisitor;
import com.kotc.db.util.Query;

public class KotcRepository<T> extends SimpleJpaRepository<T, Long> {

	private final EntityManager em;

	public KotcRepository(final Class<T> domainClass, final EntityManager em) {
		super(domainClass, em);
		this.em = em;
	}

	public Page<T> findByQuery(final Class<T> clazz, final Query query) {

		final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		final Filter filter = query.getFilter();
		List<T> resultList = null;

		final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
		final Root<T> root = criteriaQuery.from(clazz);
		final JpaFilterVisitor<T, T> jpaFilterVisitor = new JpaFilterVisitor<>(root, criteriaQuery,
				criteriaBuilder);

		// Where
		if (filter != null) {
			jpaFilterVisitor.visit(filter);
			criteriaQuery.distinct(true).where(jpaFilterVisitor.getCriteria());
		} else {
			assert filter == null;
		}

		final TypedQuery<T> tq = em.createQuery(criteriaQuery);

		final Pageable pageable = query.getPageable();
		if (pageable != null) {
			// Order By
			final Sort sort = pageable.getSort();
			if (sort != null && sort.iterator().hasNext()) {
				final List<Order> orderList = new ArrayList<>();

				sort.forEach((order) -> {
					final String fieldName = order.getProperty();
					orderList.add(order.isAscending() ? criteriaBuilder.asc(jpaFilterVisitor.getPath(fieldName))
							: criteriaBuilder.desc(jpaFilterVisitor.getPath(fieldName)));
				});
				criteriaQuery.orderBy(orderList);
			}

			// Limits
			tq.setMaxResults(pageable.getPageSize());
			tq.setFirstResult(pageable.getOffset());
		}

		// retrieval of items
		resultList = tq.getResultList();

		long total;
		if (resultList == null
				|| pageable.getPageSize() > 0 && pageable.getPageSize() <= resultList.size()) {
			// use count query at SQL level
			final CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
			final Root<T> root2 = countQuery.from(clazz);
			if (filter != null) {
				final JpaFilterVisitor<T, Long> jpaFilterVisitor2 = new JpaFilterVisitor<>(root2, countQuery,
						criteriaBuilder);
				jpaFilterVisitor2.visit(filter);
				countQuery.distinct(true).where(jpaFilterVisitor2.getCriteria());
			}
			countQuery.select(
					countQuery.isDistinct() ? criteriaBuilder.countDistinct(root2) : criteriaBuilder.count(root2));
			total = em.createQuery(countQuery).getSingleResult();
		} else {
			// use count at Java level
			total = resultList.size() + pageable.getOffset();
		}

		return new PageImpl<>(resultList, pageable, total);
	}

}
