package br.com.jitec.aps.cadastro.business.wrapper;

import java.util.List;

import br.com.jitec.aps.cadastro.data.model.APSEntity;

public class Paged<T extends APSEntity> {

	private Integer pageCount;
	private Long itemCount;
	private List<T> content;

	public Paged() {
		super();
	}

	public Paged(List<T> content, Integer pageCount, Long itemCount) {
		super();
		this.content = content;
		this.pageCount = pageCount;
		this.itemCount = itemCount;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Long getItemCount() {
		return itemCount;
	}

	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

}
