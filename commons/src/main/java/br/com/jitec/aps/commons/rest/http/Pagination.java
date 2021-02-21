package br.com.jitec.aps.commons.rest.http;

import java.util.Objects;

import br.com.jitec.aps.commons.business.exception.InvalidDataException;

public class Pagination {

	public static final String PAGE_NUMBER = "pagination-page-number";
	public static final String PAGE_SIZE = "pagination-page-size";
	public static final String TOTAL_PAGES = "pagination-total-pages";
	public static final String TOTAL_ITEMS = "pagination-total-items";

	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 25;
	private static final Integer MAX_SIZE = 100;

	public static Integer handlePage(Integer page) {
		if (Objects.isNull(page)) {
			return DEFAULT_PAGE_NUMBER;
		}
		if (page <= 0) {
			throw new InvalidDataException("page deve ser maior que 0");
		}
		return page;
	}

	public static Integer handleSize(Integer size) {
		if (Objects.isNull(size)) {
			return DEFAULT_PAGE_SIZE;
		}
		if (size <= 0) {
			throw new InvalidDataException("size deve ser maior que 0");
		}
		if (size > MAX_SIZE) {
			return MAX_SIZE;
		}
		return size;
	}

}
