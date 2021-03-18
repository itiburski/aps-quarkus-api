package br.com.jitec.aps.commons.business.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QueryBuilderTest {

	@Test
	public void getQuery_whenNothingInformed_ThenShouldReturnEmptyQuery() {
		QueryBuilder queryBuilder = new QueryBuilder();
		Assertions.assertEquals("", queryBuilder.getQuery());
	}

	@Test
	public void getQuery_whenAddConditionIsFalse_ThenShouldReturnEmptyQuery() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.addFilter(false, "someField = :someField", "someField", "paramValue");
		Assertions.assertEquals("", queryBuilder.getQuery());
	}

	@Test
	public void getQuery_whenAddConditionIsTrue_ThenShouldReturnQuery() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.addFilter(true, "someField = :someField", "someField", "paramValue");
		Assertions.assertEquals("someField = :someField", queryBuilder.getQuery());
		Assertions.assertEquals("paramValue", queryBuilder.getParams().get("someField"));
	}

	@Test
	public void getQuery_whenAddFilter_ThenShouldReturnQuery() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.addFilter("someField = :someField", "someField", "paramValue");
		Assertions.assertEquals("someField = :someField", queryBuilder.getQuery());
		Assertions.assertEquals("paramValue", queryBuilder.getParams().get("someField"));
	}

	@Test
	public void getQuery_whenAddFilterWithoutParams_ThenShouldReturnQueryAndEmptyParams() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.addFilter("someField is null");
		Assertions.assertEquals("someField is null", queryBuilder.getQuery());
		Assertions.assertTrue(queryBuilder.getParams().isEmpty());
	}

	@Test
	public void getQuery_whenSortBy_ThenShouldReturnQuery() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.setSortBy("sortField");
		Assertions.assertEquals("order by sortField", queryBuilder.getQuery());
	}

	@Test
	public void getQuery_whenAddFilterAndSortBy_ThenShouldReturnQuery() {
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.addFilter("someField = :someField", "someField", "paramValue");
		queryBuilder.setSortBy("sortField");
		Assertions.assertEquals("someField = :someField order by sortField", queryBuilder.getQuery());
		Assertions.assertEquals("paramValue", queryBuilder.getParams().get("someField"));
	}

}
