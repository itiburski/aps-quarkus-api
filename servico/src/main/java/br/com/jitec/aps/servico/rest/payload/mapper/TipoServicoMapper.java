package br.com.jitec.aps.servico.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.rest.payload.response.TipoServicoResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoServicoMapper {

	@Mapping(source = "uid", target = "tipoServicoUid")
	TipoServicoResponse toResponse(TipoServico tipoServico);

	List<TipoServicoResponse> toListResponse(List<TipoServico> tiposServico);
}
