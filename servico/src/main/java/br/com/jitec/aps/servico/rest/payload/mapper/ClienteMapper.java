package br.com.jitec.aps.servico.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.rest.payload.response.ClienteSlimResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface ClienteMapper {

	@Mapping(source = "uid", target = "clienteUid")
	ClienteSlimResponse toSlimResponse(ClienteReplica entity);

}
