package br.com.jitec.aps.servico.api;

public abstract class ApiConstants {

	public static final String TAG_TIPO_SERVICO = "Tipo de Serviço";
	public static final String TAG_TIPO_BAIXA = "Tipo de Baixa";
	public static final String TAG_ORDEM_SERVICO = "Ordem de Serviço";
	public static final String TAG_FATURA = "Fatura";

	public static final String STATUS_CODE_BAD_REQUEST = "Parâmetros inválidos na requisição";
	public static final String STATUS_CODE_NOT_FOUND = "O recurso não foi encontrado com os parâmetros informados";
	public static final String STATUS_CODE_UNPROCESSABLE_ENTITY = "O conteúdo da requisição apresenta dados inválidos ou inconsistentes";
	public static final String STATUS_CODE_SERVER_ERROR = "Ocorreu um erro ao processar a requisição";

	public static final String TIPO_SERVICO_LIST_OPERATION = "Obtém uma lista de tipos de serviço";
	public static final String TIPO_SERVICO_LIST_RESPONSE = "Retorna todos os tipos de serviço";

	public static final String TIPO_BAIXA_LIST_OPERATION = "Obtém uma lista de tipos de baixa";
	public static final String TIPO_BAIXA_LIST_RESPONSE = "Retorna todos os tipos de baixa";

	public static final String ORDEM_SERVICO_LIST_OPERATION = "Obtém uma lista de ordens de serviço, conforme configuração nos parâmetros de request";
	public static final String ORDEM_SERVICO_LIST_OPERATION_DESCRIPTION = "Valor default dos parâmetros de request: page=1 | size=25"
			+ "<br>Valores alternativos aceitos nos parâmetros de request: size={1..100}"
			+ "<br>Filtro pelos atributos da ordem de serviço (clienteUid, entradaFrom, entradaTo, entregue) serão aplicados quando o parâmetro correspondente for informado:"
			+ "<br>- clienteUid= retorna apenas ordens de serviço vinculadas ao cliente informado"
			+ "<br>- entradaFrom= retorna apenas ordens de serviço com data de entrada maior ou igual à data informada (formato YYYY-MM-DD)"
			+ "<br>- entradaTo= retorna apenas ordens de serviço com data de entrada menor ou igual à data informada (formato YYYY-MM-DD)"
			+ "<br>- entregue= se TRUE, retorna apenas ordens de serviço com data de entrega preenchida; se FALSE, retorna apenas ordens de serviço com data de entrega vazia"
			+ "<br>O response indica as configurações de paginação utilizadas nos headers: pagination-page-number, pagination-page-size, pagination-total-pages, pagination-total-items";

	public static final String ORDEM_SERVICO_LIST_RESPONSE = "Retorna todas as ordens de serviço";
	public static final String ORDEM_SERVICO_GET_OPERATION = "Obtém os dados de uma ordem de serviço específica";
	public static final String ORDEM_SERVICO_GET_RESPONSE = "Retorna a ordem de serviço correspondente ao ordemServicoUid informado";
	public static final String ORDEM_SERVICO_CREATE_OPERATION = "Cadastra uma nova ordem de serviço";
	public static final String ORDEM_SERVICO_CREATE_RESPONSE = "Retorna a nova ordem de serviço cadastrada";
	public static final String ORDEM_SERVICO_UPDATE_OPERATION = "Atualiza os dados de uma ordem de serviço existente";
	public static final String ORDEM_SERVICO_UPDATE_RESPONSE = "Retorna a ordem de serviço atualizada";
	public static final String ORDEM_SERVICO_DEFINIR_LANCAMENTO_OPERATION = "Atualiza a data de lançamento e o valor na ordem de serviço especificada";
	public static final String ORDEM_SERVICO_DEFINIR_LANCAMENTO_RESPONSE = "Retorna a ordem de serviço atualizada";

	public static final String FATURA_LIST_OPERATION = "Obtém uma lista de faturas";
	public static final String FATURA_LIST_RESPONSE = "Retorna todas as faturas";
	public static final String FATURA_CREATE_OPERATION = "Cadastra uma nova fatura";
	public static final String FATURA_CREATE_RESPONSE = "Retorna a nova fatura cadastrada";

}
