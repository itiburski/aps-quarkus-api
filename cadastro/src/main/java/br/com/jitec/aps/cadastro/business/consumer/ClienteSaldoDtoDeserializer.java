package br.com.jitec.aps.cadastro.business.consumer;

import br.com.jitec.aps.commons.business.data.ClienteSaldoDto;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ClienteSaldoDtoDeserializer extends JsonbDeserializer<ClienteSaldoDto> {

	public ClienteSaldoDtoDeserializer() {
		// pass the class to the parent
		super(ClienteSaldoDto.class);
	}

}
