package br.com.jitec.aps.servico.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.TipoBaixa;
import br.com.jitec.aps.servico.payload.response.TipoBaixaSlimResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoBaixaMapper {

	@Mapping(source = "uid", target = "tipoBaixaUid")
	TipoBaixaSlimResponse toSlimResponse(TipoBaixa tipoBaixa);

	List<TipoBaixaSlimResponse> toListSlimResponse(List<TipoBaixa> tiposBaixa);

}
