package br.com.jitec.aps.servico.business.consumer.jsob;

import br.com.jitec.aps.commons.business.data.ClienteResumidoDto;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ClienteDtoDeserializer extends JsonbDeserializer<ClienteResumidoDto> {

	public ClienteDtoDeserializer() {
		// pass the class to the parent
		super(ClienteResumidoDto.class);
	}

}
