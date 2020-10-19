package br.com.jitec.aps.rest.payload.response;

import java.net.URI;

public class ErrorResponse {

	/**
	 * The HTTP response code (optional)
	 */
	private Integer status;

	/**
	 * A brief, human-readable message about the error
	 */
	private String title;

	/**
	 * A human-readable explanation of the error
	 */
	private String detail;

	/**
	 * A URI that identifies the specific occurrence of the error
	 */
	private URI instance;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public URI getInstance() {
		return instance;
	}

	public void setInstance(URI instance) {
		this.instance = instance;
	}

	public static ErrorResponse.Builder builder() {
		return new ErrorResponse.Builder();
	}

	public static class Builder {
		private ErrorResponse response;

		public Builder() {
			response = new ErrorResponse();
		}

		public Builder withStatus(Integer status) {
			response.setStatus(status);
			return this;
		}

		public Builder withTitle(String title) {
			response.setTitle(title);
			return this;
		}

		public Builder withDetail(String detail) {
			response.setDetail(detail);
			return this;
		}

		public Builder withInstance(URI instance) {
			response.setInstance(instance);
			return this;
		}

		public ErrorResponse build() {
			return response;
		}
	}

}
