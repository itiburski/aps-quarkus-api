package br.com.jitec.aps.data.repository;

import br.com.jitec.aps.data.model.APSEntity;

public interface APSRepository<T extends APSEntity> {

	int customUpdate(T entity);

}
