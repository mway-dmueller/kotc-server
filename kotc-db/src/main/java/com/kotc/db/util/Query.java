package com.kotc.db.util;

import org.springframework.data.domain.Pageable;

import com.kotc.db.filter.domain.Filter;

public class Query {

	private Filter filter;
	private Pageable pageable;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(final Filter filter) {
		this.filter = filter;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(final Pageable pageable) {
		this.pageable = pageable;
	}

	public static Builder createBuilder() {
		return new Builder();
	}

	public static class Builder {
		private Filter filter;
		private Pageable pageable;

		private Builder() {

		}

		public Builder filter(final Filter filter) {
			this.filter = filter;
			return this;
		}

		public Builder pageable(final Pageable pageable) {
			this.pageable = pageable;
			return this;
		}

		public Query build() {
			final Query query = new Query();
			query.setFilter(filter);
			query.setPageable(pageable);

			return query;
		}
	}
}
