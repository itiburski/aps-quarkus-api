package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.jitec.aps.data.model.CategoriaCliente;
import br.com.jitec.aps.rest.payload.response.CategoriaClienteResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface CategoriaClienteMapper {

	CategoriaClienteResponse toResponse(CategoriaCliente categoriaCliente);

	List<CategoriaClienteResponse> toListResponse(List<CategoriaCliente> categoriaCliente);

}
