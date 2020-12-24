package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import br.com.jitec.aps.business.data.ClienteEmailDTO;
import br.com.jitec.aps.rest.payload.request.ClienteEmailRequest;

@Mapper(config = QuarkusMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ClienteEmailMapper {

	ClienteEmailDTO toDto(ClienteEmailRequest clienteEmail);

	List<ClienteEmailDTO> toListDto(List<ClienteEmailRequest> clientesEmail);

}
