package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.rest.payload.response.ClienteResponse;
import br.com.jitec.aps.rest.payload.response.ClienteSimplifResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface ClienteMapper {

	ClienteSimplifResponse toSimplifResponse(Cliente cliente);

	List<ClienteSimplifResponse> toSimplifListResponse(List<Cliente> cidade);

	@Mapping(source = "cliente.categoria", target = "categoriaCliente")
	ClienteResponse toResponse(Cliente cliente);

}
