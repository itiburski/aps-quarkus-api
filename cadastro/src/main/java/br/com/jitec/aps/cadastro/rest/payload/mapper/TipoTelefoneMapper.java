package br.com.jitec.aps.cadastro.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.cadastro.data.model.TipoTelefone;
import br.com.jitec.aps.cadastro.rest.payload.response.TipoTelefoneResponse;
import br.com.jitec.aps.cadastro.rest.payload.response.TipoTelefoneSlimResponse;
import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoTelefoneMapper {

	@Mapping(source = "uid", target = "tipoTelefoneUid")
	TipoTelefoneResponse toResponse(TipoTelefone tipoTelefone);

	List<TipoTelefoneResponse> toListResponse(List<TipoTelefone> tiposTelefone);

	@Mapping(source = "uid", target = "tipoTelefoneUid")
	TipoTelefoneSlimResponse toSlimResponse(TipoTelefone tipoTelefone);

	List<TipoTelefoneSlimResponse> toListSlimResponse(List<TipoTelefone> tiposTelefone);

}
