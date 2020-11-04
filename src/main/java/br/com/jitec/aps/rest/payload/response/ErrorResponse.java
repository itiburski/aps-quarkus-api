package br.com.jitec.aps.rest.payload.response;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

	private LocalDateTime timestamp;

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

	private List<ViolationResponse> violations;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

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

	public List<ViolationResponse> getViolations() {
		return violations;
	}

	public void setViolations(List<ViolationResponse> violations) {
		this.violations = violations;
	}

	public static ErrorResponse.Builder builder() {
		return new ErrorResponse.Builder();
	}

	public static class Builder {
		private ErrorResponse response;

		public Builder() {
			response = new ErrorResponse();
		}

		public Builder withTimestamp(LocalDateTime timestamp) {
			response.setTimestamp(timestamp);
			return this;
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

		public Builder withViolations(List<ViolationResponse> violations) {
			response.setViolations(violations);
			return this;
		}

		public ErrorResponse build() {
			if (response.timestamp == null) {
				response.setTimestamp(LocalDateTime.now());
			}
			return response;
		}
	}

}
