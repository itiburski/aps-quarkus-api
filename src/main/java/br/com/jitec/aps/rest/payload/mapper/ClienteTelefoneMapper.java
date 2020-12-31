package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import br.com.jitec.aps.business.data.ClienteTelefoneDTO;
import br.com.jitec.aps.rest.payload.request.ClienteTelefoneRequest;

@Mapper(config = QuarkusMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ClienteTelefoneMapper {

	ClienteTelefoneDTO toDto(ClienteTelefoneRequest clienteTelefone);

	List<ClienteTelefoneDTO> toListDto(List<ClienteTelefoneRequest> clientesTelefone);

}