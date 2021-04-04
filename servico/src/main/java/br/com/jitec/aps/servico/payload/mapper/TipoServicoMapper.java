package br.com.jitec.aps.servico.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.TipoServico;
import br.com.jitec.aps.servico.payload.response.TipoServicoSlimResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoServicoMapper {

	@Mapping(source = "uid", target = "tipoServicoUid")
	TipoServicoSlimResponse toSlimResponse(TipoServico tipoServico);

	List<TipoServicoSlimResponse> toListSlimResponse(List<TipoServico> tiposServico);
}
