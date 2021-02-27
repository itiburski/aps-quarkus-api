package br.com.jitec.aps.servico.api;

public abstract class ApiConstants {

	public static final String TAG_TIPO_SERVICO = "Tipo de serviço";

	public static final String STATUS_CODE_BAD_REQUEST = "Parâmetros inválidos na requisição";
	public static final String STATUS_CODE_NOT_FOUND = "O recurso não foi encontrado com os parâmetros informados";
	public static final String STATUS_CODE_UNPROCESSABLE_ENTITY = "O conteúdo da requisição apresenta dados inválidos ou inconsistentes";
	public static final String STATUS_CODE_SERVER_ERROR = "Ocorreu um erro ao processar a requisição";

	public static final String TIPO_SERVICO_LIST_OPERATION = "Obtém uma lista de tipos de serviço";
	public static final String TIPO_SERVICO_LIST_RESPONSE = "Retorna todos os tipos de serviço";

}
