package br.com.jitec.aps.servico.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.rest.payload.response.FaturaResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface FaturaMapper {

	@Mappings({ @Mapping(source = "uid", target = "faturaUid"),
			@Mapping(source = "cliente.uid", target = "cliente.clienteUid") })
	FaturaResponse toResponse(Fatura entity);

}
