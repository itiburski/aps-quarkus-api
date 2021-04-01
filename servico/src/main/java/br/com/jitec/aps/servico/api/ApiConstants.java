package br.com.jitec.aps.servico.api;

public abstract class ApiConstants {

	public static final String TAG_TIPO_SERVICO = "Tipo de Serviço";
	public static final String TAG_TIPO_BAIXA = "Tipo de Baixa";
	public static final String TAG_ORDEM_SERVICO = "Ordem de Serviço";
	public static final String TAG_FATURA = "Fatura";
	public static final String TAG_BAIXA = "Baixa";

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
			+ "<br>- lancado= se TRUE, retorna apenas ordens de serviço com data de lançamento preenchida; se FALSE, retorna apenas ordens de serviço com data de lançamento vazia"
			+ "<br>- faturado= se TRUE, retorna apenas ordens de serviço com fatura associada; se FALSE, retorna apenas ordens de serviço sem fatura associada"
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

	public static final String FATURA_LIST_OPERATION = "Obtém uma lista de faturas, conforme configuração nos parâmetros de request";
	public static final String FATURA_LIST_OPERATION_DESCRIPTION = "Valor default dos parâmetros de request: page=1 | size=25"
			+ "<br>Valores alternativos aceitos nos parâmetros de request: size={1..100}"
			+ "<br>Filtro pelos atributos da fatura (clienteUid, codigo, dataFrom, dataTo) serão aplicados quando o parâmetro correspondente for informado:"
			+ "<br>- clienteUid= retorna apenas faturas vinculadas ao cliente informado"
			+ "<br>- codigo= retorna apenas a fatura com o código informado"
			+ "<br>- dataFrom= retorna apenas faturas com data maior ou igual à data informada (formato YYYY-MM-DD)"
			+ "<br>- dataTo= retorna apenas faturas com data menor ou igual à data informada (formato YYYY-MM-DD)"
			+ "<br>O response indica as configurações de paginação utilizadas nos headers: pagination-page-number, pagination-page-size, pagination-total-pages, pagination-total-items";
	public static final String FATURA_LIST_RESPONSE = "Retorna todas as faturas";
	public static final String FATURA_GET_OPERATION = "Obtém os dados de uma fatura específica";
	public static final String FATURA_GET_RESPONSE = "Retorna a fatura correspondente ao faturaUid informado";
	public static final String FATURA_CREATE_OPERATION = "Cadastra uma nova fatura";
	public static final String FATURA_CREATE_RESPONSE = "Retorna a nova fatura cadastrada";
	public static final String FATURA_DELETE_OPERATION = "Exclui uma fatura existente";
	public static final String FATURA_DELETE_RESPONSE = "Indica que a fatura foi excluída. Nenhum conteúdo será retornado";

	public static final String BAIXA_LIST_OPERATION = "Obtém uma lista de baixas, conforme configuração nos parâmetros de request";
	public static final String BAIXA_LIST_OPERATION_DESCRIPTION = "Valor default dos parâmetros de request: page=1 | size=25"
			+ "<br>Valores alternativos aceitos nos parâmetros de request: size={1..100}"
			+ "<br>Filtro pelos atributos da baixa (clienteUid, tipoBaixaUid, dataFrom, dataTo) serão aplicados quando o parâmetro correspondente for informado:"
			+ "<br>- clienteUid= retorna apenas baixas vinculadas ao cliente informado"
			+ "<br>- tipoBaixaUid= retorna apenas baixas vinculadas ao tipo de baixa informado"
			+ "<br>- dataFrom= retorna apenas baixas com data maior ou igual à data informada (formato YYYY-MM-DD)"
			+ "<br>- dataTo= retorna apenas baixas com data menor ou igual à data informada (formato YYYY-MM-DD)"
			+ "<br>O response indica as configurações de paginação utilizadas nos headers: pagination-page-number, pagination-page-size, pagination-total-pages, pagination-total-items";
	public static final String BAIXA_LIST_RESPONSE = "Retorna todas as baixas";
	public static final String BAIXA_GET_OPERATION = "Obtém os dados de uma baixa específica";
	public static final String BAIXA_GET_RESPONSE = "Retorna a baixa correspondente ao baixaUid informado";
	public static final String BAIXA_CREATE_OPERATION = "Cadastra uma nova baixa";
	public static final String BAIXA_CREATE_RESPONSE = "Retorna a nova baixa cadastrada";
	public static final String BAIXA_UPDATE_OPERATION = "Atualiza os dados de uma baixa existente";
	public static final String BAIXA_UPDATE_RESPONSE = "Retorna a baixa atualizada";
	public static final String BAIXA_DELETE_OPERATION = "Exclui uma baixa existente";
	public static final String BAIXA_DELETE_RESPONSE = "Indica que a baixa foi excluída. Nenhum conteúdo será retornado";

}
