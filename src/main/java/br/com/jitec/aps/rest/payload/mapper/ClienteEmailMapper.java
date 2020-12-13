package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.jitec.aps.data.model.ClienteEmail;
import br.com.jitec.aps.rest.payload.response.ClienteEmailResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface ClienteEmailMapper {

	ClienteEmailResponse toResponse(ClienteEmail clienteEmail);

	List<ClienteEmailResponse> toListResponse(List<ClienteEmail> clienteEmail);

}
