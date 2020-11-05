package br.com.jitec.aps.api;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
public class JSONBContextResolver implements ContextResolver<Jsonb> {

	@Override
	public Jsonb getContext(Class<?> type) {
		JsonbConfig config = new JsonbConfig().withNullValues(true);
		return JsonbBuilder.newBuilder().withConfig(config).build();
	}

}
