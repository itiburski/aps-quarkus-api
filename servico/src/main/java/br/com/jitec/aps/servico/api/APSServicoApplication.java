package br.com.jitec.aps.servico.api;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

@OpenAPIDefinition(
	    info = @Info(
				title = "APS-Servico", description = "API para controlar o módulo de serviço do APS - Automação de Prestação de Serviço",
				version = "0.1.0",
	        license = @License(
						name = "MIT License", url = "https://opensource.org/licenses/MIT"))
	)
public class APSServicoApplication extends Application {

}
