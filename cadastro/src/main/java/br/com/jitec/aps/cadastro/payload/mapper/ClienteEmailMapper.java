package br.com.jitec.aps.cadastro.payload.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import br.com.jitec.aps.cadastro.business.data.ClienteEmailDTO;
import br.com.jitec.aps.cadastro.payload.request.ClienteEmailCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteEmailUpdateRequest;
import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ClienteEmailMapper {

	ClienteEmailDTO toDto(ClienteEmailCreateRequest clienteEmail);

	List<ClienteEmailDTO> toListDto(List<ClienteEmailCreateRequest> clientesEmail);

	ClienteEmailDTO toUpdateDto(ClienteEmailUpdateRequest clienteEmail);

	List<ClienteEmailDTO> toListUpdateDto(List<ClienteEmailUpdateRequest> clientesEmail);
	
}
