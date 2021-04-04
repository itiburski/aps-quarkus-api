package br.com.jitec.aps.cadastro.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.cadastro.data.model.ClienteEmail;
import br.com.jitec.aps.cadastro.data.model.ClienteTelefone;
import br.com.jitec.aps.cadastro.payload.response.ClienteEmailResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteSlimResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteTelefoneResponse;
import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;

@Mapper(config = QuarkusMapperConfig.class, uses = { CategoriaClienteMapper.class, CidadeMapper.class,
		TipoTelefoneMapper.class })
public interface ClienteMapper {

	@Mapping(source = "uid", target = "clienteUid")
	ClienteSlimResponse toSlimResponse(Cliente cliente);

	List<ClienteSlimResponse> toListSlimResponse(List<Cliente> cliente);

	@Mappings({ @Mapping(source = "uid", target = "clienteUid"),
			@Mapping(source = "categoria", target = "categoriaCliente") })
	ClienteResponse toResponse(Cliente cliente);

	@Mapping(source = "uid", target = "emailUid")
	ClienteEmailResponse toResponse(ClienteEmail clienteEmail);

	@Mapping(source = "uid", target = "telefoneUid")
	ClienteTelefoneResponse toResponse(ClienteTelefone clienteTelefone);

}
