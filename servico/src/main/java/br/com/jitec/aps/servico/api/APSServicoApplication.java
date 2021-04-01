package br.com.jitec.aps.servico.api;

import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
	tags = { @Tag(name = ServicoApiConstants.TAG_ORDENS_SERVICO, description =  ServicoApiConstants.TAG_ORDENS_SERVICO_DESCRIPTION),
			@Tag(name = ServicoApiConstants.TAG_FATURAS, description =  ServicoApiConstants.TAG_FATURAS_DESCRIPTION),
			@Tag(name = ServicoApiConstants.TAG_BAIXAS, description =  ServicoApiConstants.TAG_BAIXAS_DESCRIPTION),
			@Tag(name = ServicoApiConstants.TAG_TIPOS_BAIXA, description =  ServicoApiConstants.TAG_TIPOS_BAIXA_DESCRIPTION),
			@Tag(name = ServicoApiConstants.TAG_TIPOS_SERVICO, description = ServicoApiConstants.TAG_TIPOS_SERVICO_DESCRIPTION) },
    info = @Info(
			title = ServicoApiConstants.API_TITLE, description = ServicoApiConstants.API_DESCRIPTION,
			version = ServicoApiConstants.API_VERSION,
    license = @License(name = ServicoApiConstants.LICENSE_NAME, url = ServicoApiConstants.LICENSE_URL))
)
public class APSServicoApplication extends Application {

}
