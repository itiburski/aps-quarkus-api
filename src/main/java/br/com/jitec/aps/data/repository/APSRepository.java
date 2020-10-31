package br.com.jitec.aps.data.repository;

import java.util.Optional;
import java.util.UUID;

import br.com.jitec.aps.data.model.APSEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface APSRepository<T extends APSEntity> extends PanacheRepository<T> {

	default Optional<T> findByUid(UUID uid) {
		return find("uid", uid).firstResultOptional();
	}

}
