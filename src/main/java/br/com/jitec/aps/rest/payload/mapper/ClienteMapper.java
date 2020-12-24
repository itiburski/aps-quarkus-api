package br.com.jitec.aps.rest.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import br.com.jitec.aps.data.model.Cliente;
import br.com.jitec.aps.data.model.ClienteEmail;
import br.com.jitec.aps.data.model.ClienteTelefone;
import br.com.jitec.aps.data.model.TipoTelefone;
import br.com.jitec.aps.rest.payload.response.ClienteEmailResponse;
import br.com.jitec.aps.rest.payload.response.ClienteResponse;
import br.com.jitec.aps.rest.payload.response.ClienteSimplifResponse;
import br.com.jitec.aps.rest.payload.response.ClienteTelefoneResponse;
import br.com.jitec.aps.rest.payload.response.TipoTelefoneResponse;

@Mapper(config = QuarkusMapperConfig.class)
public interface ClienteMapper {

	@Mapping(source = "uid", target = "clienteUid")
	ClienteSimplifResponse toSimplifResponse(Cliente cliente);

	List<ClienteSimplifResponse> toSimplifListResponse(List<Cliente> cidade);

	@Mappings({ @Mapping(source = "uid", target = "clienteUid"),
			@Mapping(source = "cliente.categoria.uid", target = "categoriaCliente.categoriaClienteUid"),
			@Mapping(source = "cliente.cidade.uid", target = "cidade.cidadeUid") })
	ClienteResponse toResponse(Cliente cliente);

	@Mapping(source = "uid", target = "emailUid")
	ClienteEmailResponse toResponse(ClienteEmail clienteEmail);

	@Mapping(source = "uid", target = "telefoneUid")
	ClienteTelefoneResponse toResponse(ClienteTelefone clienteTelefone);

	@Mapping(source = "uid", target = "tipoTelefoneUid")
	TipoTelefoneResponse toResponse(TipoTelefone tipoTelefone);

}
