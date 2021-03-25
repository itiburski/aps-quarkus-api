package br.com.jitec.aps.servico.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.ClienteReplica;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.rest.payload.response.ClienteResponse;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoResponse;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoSimpleResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface OrdemServicoMapper {

	@Mappings({ @Mapping(source = "uid", target = "ordemServicoUid"),
			@Mapping(source = "tipoServico.uid", target = "tipoServico.tipoServicoUid"),
			@Mapping(source = "fatura.codigo", target = "codigoFatura") })
	OrdemServicoSimpleResponse toSimpleResponse(OrdemServico entity);

	@Mappings({ @Mapping(source = "uid", target = "ordemServicoUid"),
			@Mapping(source = "tipoServico.uid", target = "tipoServico.tipoServicoUid"),
			@Mapping(source = "fatura.codigo", target = "codigoFatura") })
	OrdemServicoResponse toResponse(OrdemServico entity);

	@Mapping(source = "uid", target = "clienteUid")
	ClienteResponse toResponse(ClienteReplica entity);

}
