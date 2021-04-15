package br.com.jitec.aps.cadastro.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import br.com.jitec.aps.cadastro.data.model.Cliente;
import br.com.jitec.aps.cadastro.data.model.ClienteEmail;
import br.com.jitec.aps.cadastro.data.model.ClienteTelefone;
import br.com.jitec.aps.cadastro.payload.request.ClienteCreateRequest;
import br.com.jitec.aps.cadastro.payload.request.ClienteUpdateRequest;
import br.com.jitec.aps.cadastro.payload.response.ClienteEmailResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteSlimResponse;
import br.com.jitec.aps.cadastro.payload.response.ClienteTelefoneResponse;
import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.commons.payload.mapper.ToEntity;

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

	@ToEntity
	@Mapping(target = "ativo", ignore = true)
	@Mapping(target = "categoria", ignore = true)
	@Mapping(target = "cidade", ignore = true)
	@Mapping(target = "codigo", ignore = true)
	@Mapping(target = "emails", ignore = true)
	@Mapping(target = "saldo", ignore = true)
	@Mapping(target = "telefones", ignore = true)
	Cliente toCliente(ClienteCreateRequest request);

	@ToEntity
	@Mapping(target = "ativo", source = "ativo", defaultValue = "false")
	@Mapping(target = "categoria", ignore = true)
	@Mapping(target = "cidade", ignore = true)
	@Mapping(target = "codigo", ignore = true)
	@Mapping(target = "emails", ignore = true)
	@Mapping(target = "saldo", ignore = true)
	@Mapping(target = "telefones", ignore = true)
	void update(ClienteUpdateRequest request, @MappingTarget Cliente entity);

}
