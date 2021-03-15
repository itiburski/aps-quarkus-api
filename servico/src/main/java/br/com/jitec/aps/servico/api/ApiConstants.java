package br.com.jitec.aps.servico.api;

public abstract class ApiConstants {

	public static final String TAG_TIPO_SERVICO = "Tipo de Serviço";
	public static final String TAG_TIPO_BAIXA = "Tipo de Baixa";
	public static final String TAG_ORDEM_SERVICO = "Ordem de Serviço";

	public static final String STATUS_CODE_BAD_REQUEST = "Parâmetros inválidos na requisição";
	public static final String STATUS_CODE_NOT_FOUND = "O recurso não foi encontrado com os parâmetros informados";
	public static final String STATUS_CODE_UNPROCESSABLE_ENTITY = "O conteúdo da requisição apresenta dados inválidos ou inconsistentes";
	public static final String STATUS_CODE_SERVER_ERROR = "Ocorreu um erro ao processar a requisição";

	public static final String TIPO_SERVICO_LIST_OPERATION = "Obtém uma lista de tipos de serviço";
	public static final String TIPO_SERVICO_LIST_RESPONSE = "Retorna todos os tipos de serviço";

	public static final String TIPO_BAIXA_LIST_OPERATION = "Obtém uma lista de tipos de baixa";
	public static final String TIPO_BAIXA_LIST_RESPONSE = "Retorna todos os tipos de baixa";

	public static final String ORDEM_SERVICO_LIST_OPERATION = "Obtém uma lista de ordens de serviço";
	public static final String ORDEM_SERVICO_LIST_RESPONSE = "Retorna todas as ordens de serviço";
	public static final String ORDEM_SERVICO_GET_OPERATION = "Obtém os dados de uma ordem de serviço específica";
	public static final String ORDEM_SERVICO_GET_RESPONSE = "Retorna a ordem de serviço correspondente ao ordemServicoUid informado";
	public static final String ORDEM_SERVICO_CREATE_OPERATION = "Cadastra uma nova ordem de serviço";
	public static final String ORDEM_SERVICO_CREATE_RESPONSE = "Retorna a nova ordem de serviço cadastrada";
	public static final String ORDEM_SERVICO_UPDATE_OPERATION = "Atualiza os dados de uma ordem de serviço existente";
	public static final String ORDEM_SERVICO_UPDATE_RESPONSE = "Retorna a ordem de serviço atualizada";

}
