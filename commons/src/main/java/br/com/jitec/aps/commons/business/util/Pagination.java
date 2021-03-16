package br.com.jitec.aps.commons.business.util;

import java.util.Objects;

import br.com.jitec.aps.commons.business.exception.InvalidDataException;

public class Pagination {

	private static final Integer DEFAULT_PAGE_NUMBER = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 25;
	private static final Integer MAX_SIZE = 100;

	private Integer page;
	private Integer size;

	public Integer getPage() {
		return page;
	}

	public Integer getPageZeroBased() {
		return page - 1;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public static Pagination.Builder builder() {
		return new Pagination.Builder();
	}

	public static class Builder {
		private Pagination instance;

		public Builder() {
			instance = new Pagination();
		}

		public Builder withPage(Integer page) {
			instance.setPage(handlePage(page));
			return this;
		}

		public Builder withSize(Integer size) {
			instance.setSize(handleSize(size));
			return this;
		}

		public Pagination build() {
			return instance;
		}

		private Integer handlePage(Integer page) {
			if (Objects.isNull(page)) {
				return DEFAULT_PAGE_NUMBER;
			}
			if (page <= 0) {
				throw new InvalidDataException("page deve ser maior que 0");
			}
			return page;
		}

		private Integer handleSize(Integer size) {
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

}
