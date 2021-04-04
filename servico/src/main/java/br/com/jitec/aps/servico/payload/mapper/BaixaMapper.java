package br.com.jitec.aps.servico.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.Baixa;
import br.com.jitec.aps.servico.payload.response.BaixaResponse;
import br.com.jitec.aps.servico.payload.response.BaixaSlimResponse;

@Mapper(config = QuarkusMapperConfig.class, uses = { ClienteMapper.class, TipoBaixaMapper.class })
public interface BaixaMapper {

	@Mapping(source = "uid", target = "baixaUid")
	BaixaSlimResponse toSlimResponse(Baixa entity);

	List<BaixaSlimResponse> toListSlimResponse(List<Baixa> entitites);

	@Mapping(source = "uid", target = "baixaUid")
	BaixaResponse toResponse(Baixa entity);

}
