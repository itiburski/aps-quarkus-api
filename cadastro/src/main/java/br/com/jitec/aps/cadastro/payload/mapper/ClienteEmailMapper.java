package br.com.jitec.aps.cadastro.payload.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import br.com.jitec.aps.cadastro.business.data.ClienteEmailDTO;
import br.com.jitec.aps.cadastro.payload.request.ClienteEmailRequest;
import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ClienteEmailMapper {

	ClienteEmailDTO toDto(ClienteEmailRequest clienteEmail);

	List<ClienteEmailDTO> toListDto(List<ClienteEmailRequest> clientesEmail);

}
