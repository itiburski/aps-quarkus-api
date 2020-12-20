package br.com.jitec.aps.api;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
		tags = { @Tag(name = ApiConstants.TAG_CATEGORIAS_CLIENTE, description = "Operações relacionadas a Categorias de Cliente"),
				@Tag(name = ApiConstants.TAG_CIDADES, description = "Operações relacionadas a Cidades"),
				@Tag(name = ApiConstants.TAG_CLIENTES, description = "Operações relacionadas a Clientes"),
				@Tag(name = ApiConstants.TAG_TIPO_TELEFONE, description = "Operações relacionadas a Tipos de Telefone") },
	    info = @Info(
				title = "APS API", description = "API para controlar a Automação de Prestação de Serviço - APS",
				version = "1.0.0",
	        license = @License(
						name = "MIT License", url = "https://opensource.org/licenses/MIT"))
	)
public class APSApplication extends Application {

}
