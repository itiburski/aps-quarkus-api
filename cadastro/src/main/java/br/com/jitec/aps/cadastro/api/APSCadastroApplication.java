package br.com.jitec.aps.cadastro.api;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
	tags = { @Tag(name = CadastroApiConstants.TAG_CATEGORIAS_CLIENTE, description = CadastroApiConstants.TAG_CATEGORIAS_CLIENTE_DESCRIPTION),
			@Tag(name = CadastroApiConstants.TAG_CIDADES, description = CadastroApiConstants.TAG_CIDADES_DESCRIPTION),
			@Tag(name = CadastroApiConstants.TAG_CLIENTES, description =  CadastroApiConstants.TAG_CLIENTES_DESCRIPTION),
			@Tag(name = CadastroApiConstants.TAG_TIPOS_TELEFONE, description =  CadastroApiConstants.TAG_TIPOS_TELEFONE_DESCRIPTION) },
    info = @Info(
			title = CadastroApiConstants.API_TITLE, description = CadastroApiConstants.API_DESCRIPTION,
			version = CadastroApiConstants.API_VERSION,
    license = @License(name = CadastroApiConstants.LICENSE_NAME, url = CadastroApiConstants.LICENSE_URL))
)
public class APSCadastroApplication extends Application {

}
