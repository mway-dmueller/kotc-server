package com.kotc.db.filter.util;

import com.kotc.db.filter.domain.Filter;

public interface FilterVisitor {

	void visit(Filter filter);
}
