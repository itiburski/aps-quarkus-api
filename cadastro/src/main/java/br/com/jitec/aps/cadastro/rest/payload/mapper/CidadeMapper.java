package br.com.jitec.aps.cadastro.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.cadastro.data.model.Cidade;
import br.com.jitec.aps.cadastro.rest.payload.response.CidadeResponse;
import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class)
public interface CidadeMapper {

	@Mapping(source = "uid", target = "cidadeUid")
	CidadeResponse toResponse(Cidade cidade);

	List<CidadeResponse> toListResponse(List<Cidade> cidade);

}
