package br.com.jitec.aps.cadastro.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.cadastro.data.model.CategoriaCliente;
import br.com.jitec.aps.cadastro.rest.payload.response.CategoriaClienteResponse;
import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class)
public interface CategoriaClienteMapper {

	@Mapping(source = "uid", target = "categoriaClienteUid")
	CategoriaClienteResponse toResponse(CategoriaCliente categoriaCliente);

	List<CategoriaClienteResponse> toListResponse(List<CategoriaCliente> categoriaCliente);

}
