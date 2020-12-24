package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.data.model.Cidade;
import br.com.jitec.aps.rest.payload.response.CidadeResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface CidadeMapper {

	@Mapping(source = "uid", target = "cidadeUid")
	CidadeResponse toResponse(Cidade cidade);

	List<CidadeResponse> toListResponse(List<Cidade> cidade);

}
