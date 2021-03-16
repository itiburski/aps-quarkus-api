package br.com.jitec.aps.commons.rest.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.jitec.aps.commons.business.exception.InvalidDataException;
import br.com.jitec.aps.commons.business.util.Pagination;

public class PaginationTest {

	@Test
	public void build_WhenPageNull_ShouldReturnDefaultPage() {
		Assertions.assertEquals(1, Pagination.builder().withPage(null).build().getPage());
	}

	@Test
	public void build_WhenValidPage_ShouldReturnRequestedPage() {
		Assertions.assertEquals(1, Pagination.builder().withPage(1).build().getPage());
		Assertions.assertEquals(15, Pagination.builder().withPage(15).build().getPage());
		Assertions.assertEquals(100, Pagination.builder().withPage(100).build().getPage());
	}

	@Test
	public void build_WhenPageIsInvalid_ShouldThrowException() {
		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> Pagination.builder().withPage(0).build(), "should have thrown InvalidDataException");
		Assertions.assertEquals("page deve ser maior que 0", thrown.getMessage());
		thrown = Assertions.assertThrows(InvalidDataException.class, () -> Pagination.builder().withPage(-1).build(),
				"should have thrown InvalidDataException");
		Assertions.assertEquals("page deve ser maior que 0", thrown.getMessage());
	}

	@Test
	public void build_WhenSizeNull_ShouldReturnDefaultPage() {
		Assertions.assertEquals(25, Pagination.builder().withSize(null).build().getSize());
	}

	@Test
	public void build_WhenValidSize_ShouldReturnRequestedSize() {
		Assertions.assertEquals(1, Pagination.builder().withSize(1).build().getSize());
		Assertions.assertEquals(15, Pagination.builder().withSize(15).build().getSize());
		Assertions.assertEquals(100, Pagination.builder().withSize(100).build().getSize());
	}

	@Test
	public void build_WhenSizeIsInvalid_ShouldThrowException() {
		Exception thrown = Assertions.assertThrows(InvalidDataException.class,
				() -> Pagination.builder().withSize(0).build(), "should have thrown InvalidDataException");
		Assertions.assertEquals("size deve ser maior que 0", thrown.getMessage());
		thrown = Assertions.assertThrows(InvalidDataException.class, () -> Pagination.builder().withSize(-1).build(),
				"should have thrown InvalidDataException");
		Assertions.assertEquals("size deve ser maior que 0", thrown.getMessage());
	}

	@Test
	public void build_WhenSizeGreaterThan100_ShouldReturnMaxSize() {
		Assertions.assertEquals(100, Pagination.builder().withSize(101).build().getSize());
		Assertions.assertEquals(100, Pagination.builder().withSize(150).build().getSize());
		Assertions.assertEquals(100, Pagination.builder().withSize(999).build().getSize());
	}

}
