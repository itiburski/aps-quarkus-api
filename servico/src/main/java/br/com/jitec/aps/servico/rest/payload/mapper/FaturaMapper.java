package br.com.jitec.aps.servico.rest.payload.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.rest.payload.response.FaturaResponse;
import br.com.jitec.aps.servico.rest.payload.response.FaturaSimpleResponse;
import br.com.jitec.aps.servico.rest.payload.response.OrdemServicoSimpleResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface FaturaMapper {

	@Mappings({ @Mapping(source = "uid", target = "faturaUid"),
			@Mapping(source = "cliente.uid", target = "cliente.clienteUid") })
	FaturaSimpleResponse toSimpleResponse(Fatura entity);

	@Mappings({ @Mapping(source = "uid", target = "faturaUid"),
			@Mapping(source = "cliente.uid", target = "cliente.clienteUid") })
	FaturaResponse toResponse(Fatura entity);

	@Mappings({ @Mapping(source = "uid", target = "ordemServicoUid"),
			@Mapping(source = "tipoServico.uid", target = "tipoServico.tipoServicoUid"),
			@Mapping(source = "cliente.uid", target = "cliente.clienteUid"),
		@Mapping(source = "fatura.codigo", target = "codigoFatura") })	
	OrdemServicoSimpleResponse toSimpleResponse(OrdemServico entity);

}
