package br.com.jitec.aps.cadastro.payload.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import br.com.jitec.aps.cadastro.business.data.ClienteTelefoneDTO;
import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteTelefoneUpdateRequest;
import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ClienteTelefoneMapper {

	ClienteTelefoneDTO toDto(ClienteTelefoneCreateRequest clienteTelefone);

	List<ClienteTelefoneDTO> toListDto(List<ClienteTelefoneCreateRequest> clientesTelefone);

	ClienteTelefoneDTO toUpdateDto(ClienteTelefoneUpdateRequest clienteTelefone);

	List<ClienteTelefoneDTO> toListUpdateDto(List<ClienteTelefoneUpdateRequest> clientesTelefone);

}
