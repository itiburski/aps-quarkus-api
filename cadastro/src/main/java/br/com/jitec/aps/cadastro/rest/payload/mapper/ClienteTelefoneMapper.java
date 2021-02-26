package br.com.jitec.aps.cadastro.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import br.com.jitec.aps.cadastro.business.data.ClienteTelefoneDTO;
import br.com.jitec.aps.cadastro.rest.payload.request.ClienteTelefoneRequest;
import br.com.jitec.aps.commons.rest.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class, builder = @Builder(disableBuilder = true))
public interface ClienteTelefoneMapper {

	ClienteTelefoneDTO toDto(ClienteTelefoneRequest clienteTelefone);

	List<ClienteTelefoneDTO> toListDto(List<ClienteTelefoneRequest> clientesTelefone);

}
