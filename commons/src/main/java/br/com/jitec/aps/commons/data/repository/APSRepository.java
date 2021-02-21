package br.com.jitec.aps.commons.data.repository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import br.com.jitec.aps.commons.data.model.APSEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public interface APSRepository<T extends APSEntity> extends PanacheRepository<T> {

	default Optional<T> findByUid(UUID uid) {
		return find("uid", uid).firstResultOptional();
	}

	default Optional<T> findByUidVersion(UUID uid, Integer version) {
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("uid", uid);
		params.put("version", version);
		return find("uid = :uid and version = :version", params).firstResultOptional();
	}

}
