package br.com.jitec.aps.commons.business.util;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QueryBuilder {

	String sortBy = null;
	List<String> where = new LinkedList<>();
	Map<String, Object> params = new LinkedHashMap<>();

	public void addFilter(String whereClause, String paramName, Object paramValue) {
		where.add(whereClause);
		params.put(paramName, paramValue);
	}

	public void addFilter(Boolean addCondition, String whereClause, String paramName, Object paramValue) {
		if (addCondition) {
			where.add(whereClause);
			params.put(paramName, paramValue);
		}
	}

	public void setSortBy(String sortField) {
		this.sortBy = sortField;
	}

	public String getQuery() {
		StringBuilder query = new StringBuilder(String.join(" and ", where));
		if (Objects.nonNull(sortBy)) {
			query.append(" order by ").append(sortBy);
		}
		return query.toString().trim();
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
