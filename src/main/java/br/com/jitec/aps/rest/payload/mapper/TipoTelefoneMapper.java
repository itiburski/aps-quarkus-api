package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.jitec.aps.data.model.TipoTelefone;
import br.com.jitec.aps.rest.payload.response.TipoTelefoneResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoTelefoneMapper {

	TipoTelefoneResponse toResponse(TipoTelefone tipoTelefone);

	List<TipoTelefoneResponse> toListResponse(List<TipoTelefone> tiposTelefone);

}
