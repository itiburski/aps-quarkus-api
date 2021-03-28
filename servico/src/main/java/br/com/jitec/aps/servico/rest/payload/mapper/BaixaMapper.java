package br.com.jitec.aps.servico.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.rest.payload.response.BaixaSimpleResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface BaixaMapper {

	@Mappings({ @Mapping(source = "uid", target = "baixaUid"),
			@Mapping(source = "cliente.uid", target = "cliente.clienteUid"),
			@Mapping(source = "tipoBaixa.uid", target = "tipoBaixa.tipoBaixaUid") })
	BaixaSimpleResponse toSimpleResponse(Baixa entity);

}
