package br.com.jitec.aps.servico.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.TipoBaixa;
import br.com.jitec.aps.servico.rest.payload.response.TipoBaixaResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoBaixaMapper {

	@Mapping(source = "uid", target = "tipoBaixaUid")
	TipoBaixaResponse toResponse(TipoBaixa tipoBaixa);

	List<TipoBaixaResponse> toListResponse(List<TipoBaixa> tiposBaixa);
}
