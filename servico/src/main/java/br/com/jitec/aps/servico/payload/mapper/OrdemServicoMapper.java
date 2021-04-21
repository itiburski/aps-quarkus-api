package br.com.jitec.aps.servico.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.commons.payload.mapper.ToEntity;
import br.com.jitec.aps.servico.data.model.OrdemServico;
import br.com.jitec.aps.servico.payload.request.OrdemServicoCreateRequest;
import br.com.jitec.aps.servico.payload.request.OrdemServicoUpdateRequest;
import br.com.jitec.aps.servico.payload.response.OrdemServicoResponse;
import br.com.jitec.aps.servico.payload.response.OrdemServicoSlimResponse;

@Mapper(config = QuarkusMapperConfig.class, uses = { ClienteMapper.class, TipoServicoMapper.class })

public interface OrdemServicoMapper {

	@Mappings({ @Mapping(source = "uid", target = "ordemServicoUid"),
			@Mapping(source = "fatura.codigo", target = "codigoFatura") })
	OrdemServicoSlimResponse toSlimResponse(OrdemServico entity);

	List<OrdemServicoSlimResponse> toListSlimResponse(List<OrdemServico> entities);

	@Mappings({ @Mapping(source = "uid", target = "ordemServicoUid"),
			@Mapping(source = "fatura.codigo", target = "codigoFatura") })
	OrdemServicoResponse toResponse(OrdemServico entity);

	@ToEntity
	@Mapping(target = "numero", ignore = true)
	@Mapping(target = "cliente", ignore = true)
	@Mapping(target = "tipoServico", ignore = true)
	@Mapping(target = "lancamento", ignore = true)
	@Mapping(target = "fatura", ignore = true)
	OrdemServico toOrdemServico(OrdemServicoCreateRequest request);

	@ToEntity
	@Mapping(target = "numero", ignore = true)
	@Mapping(target = "cliente", ignore = true)
	@Mapping(target = "tipoServico", ignore = true)
	@Mapping(target = "lancamento", ignore = true)
	@Mapping(target = "fatura", ignore = true)
	void update(OrdemServicoUpdateRequest request, @MappingTarget OrdemServico entity);
	
}
