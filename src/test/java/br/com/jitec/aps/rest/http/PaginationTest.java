package br.com.jitec.aps.rest.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.com.jitec.aps.business.exception.InvalidDataException;

public class PaginationTest {

	@Test
	public void handlePage_WhenNull_ShouldReturnDefaultPageNumber() {
		Assertions.assertEquals(1, Pagination.handlePage(null));
	}

	@Test
	public void handlePage_WhenValid_ShouldReturnRequestedPage() {
		Assertions.assertEquals(1, Pagination.handlePage(1));
		Assertions.assertEquals(15, Pagination.handlePage(15));
		Assertions.assertEquals(100, Pagination.handlePage(100));
	}

	@Test
	public void handlePage_WhenInvalid_ShouldThrowException() {
		Exception thrown = Assertions.assertThrows(InvalidDataException.class, () -> Pagination.handlePage(0),
				"should have thrown InvalidDataException");
		Assertions.assertEquals("page deve ser maior que 0", thrown.getMessage());
		thrown = Assertions.assertThrows(InvalidDataException.class, () -> Pagination.handlePage(-1),
				"should have thrown InvalidDataException");
		Assertions.assertEquals("page deve ser maior que 0", thrown.getMessage());
	}

	@Test
	public void handleSize_WhenNull_ShouldReturnDefaultPageNumber() {
		Assertions.assertEquals(25, Pagination.handleSize(null));
	}

	@Test
	public void handleSize_WhenValid_ShouldReturnRequestedSize() {
		Assertions.assertEquals(1, Pagination.handleSize(1));
		Assertions.assertEquals(15, Pagination.handleSize(15));
		Assertions.assertEquals(100, Pagination.handleSize(100));
	}

	@Test
	public void handleSize_WhenInvalid_ShouldThrowException() {
		Exception thrown = Assertions.assertThrows(InvalidDataException.class, () -> Pagination.handleSize(0),
				"should have thrown InvalidDataException");
		Assertions.assertEquals("size deve ser maior que 0", thrown.getMessage());
		thrown = Assertions.assertThrows(InvalidDataException.class, () -> Pagination.handleSize(-1),
				"should have thrown InvalidDataException");
		Assertions.assertEquals("size deve ser maior que 0", thrown.getMessage());
	}

	@Test
	public void handleSize_WhenGreaterThan100_ShouldReturnMaxSize() {
		Assertions.assertEquals(100, Pagination.handleSize(101));
		Assertions.assertEquals(100, Pagination.handleSize(150));
		Assertions.assertEquals(100, Pagination.handleSize(999));
	}

}
