package br.com.jitec.aps.servico.payload.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jitec.aps.commons.payload.mapper.QuarkusMapperConfig;
import br.com.jitec.aps.servico.data.model.Fatura;
import br.com.jitec.aps.servico.payload.response.FaturaResponse;
import br.com.jitec.aps.servico.payload.response.FaturaSlimResponse;

@Mapper(config = QuarkusMapperConfig.class, uses = { ClienteMapper.class, OrdemServicoMapper.class })
public interface FaturaMapper {

	@Mapping(source = "uid", target = "faturaUid")
	FaturaSlimResponse toSlimResponse(Fatura entity);

	List<FaturaSlimResponse> toListSlimResponse(List<Fatura> entities);

	@Mapping(source = "uid", target = "faturaUid")
	FaturaResponse toResponse(Fatura entity);

}
