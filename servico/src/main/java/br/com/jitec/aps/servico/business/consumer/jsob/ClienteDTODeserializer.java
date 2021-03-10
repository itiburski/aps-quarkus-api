package br.com.jitec.aps.servico.business.consumer.jsob;

import br.com.jitec.aps.commons.business.dto.ClienteResumidoDTO;
import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class ClienteDTODeserializer extends JsonbDeserializer<ClienteResumidoDTO> {

	public ClienteDTODeserializer() {
		// pass the class to the parent
		super(ClienteResumidoDTO.class);
	}

}
