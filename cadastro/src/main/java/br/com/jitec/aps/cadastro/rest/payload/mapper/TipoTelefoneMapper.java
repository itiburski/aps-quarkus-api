package br.com.jitec.aps.cadastro.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.cadastro.data.model.TipoTelefone;
import br.com.jitec.aps.cadastro.rest.payload.response.TipoTelefoneResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface TipoTelefoneMapper {

	@Mapping(source = "uid", target = "tipoTelefoneUid")
	TipoTelefoneResponse toResponse(TipoTelefone tipoTelefone);

	List<TipoTelefoneResponse> toListResponse(List<TipoTelefone> tiposTelefone);

}
