package br.com.jitec.aps.cadastro.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.cadastro.data.model.Cidade;
import br.com.jitec.aps.cadastro.payload.response.CidadeResponse;
import br.com.jitec.aps.cadastro.payload.response.CidadeSlimResponse;
import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class)
public interface CidadeMapper {

	@Mapping(source = "uid", target = "cidadeUid")
	CidadeResponse toResponse(Cidade cidade);

	List<CidadeResponse> toListResponse(List<Cidade> cidade);

	@Mapping(source = "uid", target = "cidadeUid")
	CidadeSlimResponse toSlimResponse(Cidade cidade);

	List<CidadeSlimResponse> toListSlimResponse(List<Cidade> cidade);

}
