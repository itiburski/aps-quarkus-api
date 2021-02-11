package br.com.jitec.aps.cadastro.api;

import javax.inject.Singleton;
import javax.json.bind.JsonbConfig;

import io.quarkus.jsonb.JsonbConfigCustomizer;

@Singleton
public class APSJsonbConfigCustomizer implements JsonbConfigCustomizer {

	@Override
	public void customize(JsonbConfig jsonbConfig) {
		jsonbConfig.withNullValues(true);
	}

}
