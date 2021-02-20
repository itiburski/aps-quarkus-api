package br.com.jitec.aps.cadastro.api;

public abstract class ApiConstants {

	public static final String TAG_CATEGORIAS_CLIENTE = "Categorias de Cliente";
	public static final String TAG_CIDADES = "Cidades";
	public static final String TAG_CLIENTES = "Clientes";
	public static final String TAG_TIPO_TELEFONE = "Tipo de Telefone";

	public static final String STATUS_CODE_BAD_REQUEST = "Parâmetros inválidos na requisição";
	public static final String STATUS_CODE_NOT_FOUND = "O recurso não foi encontrado com os parâmetros informados";
	public static final String STATUS_CODE_UNPROCESSABLE_ENTITY = "O conteúdo da requisição apresenta dados inválidos ou inconsistentes";
	public static final String STATUS_CODE_SERVER_ERROR = "Ocorreu um erro ao processar a requisição";

	public static final String CATEGORIA_CLIENTE_LIST_OPERATION = "Obtém uma lista de categorias de cliente";
	public static final String CATEGORIA_CLIENTE_LIST_RESPONSE = "Retorna todas as categorias de cliente";
	public static final String CATEGORIA_CLIENTE_GET_OPERATION = "Obtém os dados de uma categoria de cliente específica";
	public static final String CATEGORIA_CLIENTE_GET_RESPONSE = "Retorna a categoria de cliente correspondente ao categoriaClienteUid informado";
	public static final String CATEGORIA_CLIENTE_CREATE_OPERATION = "Cadastra uma nova categoria de cliente";
	public static final String CATEGORIA_CLIENTE_CREATE_RESPONSE = "Retorna a nova categoria de cliente cadastrada";
	public static final String CATEGORIA_CLIENTE_UPDATE_OPERATION = "Atualiza os dados de uma categoria de cliente existente";
	public static final String CATEGORIA_CLIENTE_UPDATE_RESPONSE = "Retorna a categoria de cliente atualizada";
	public static final String CATEGORIA_CLIENTE_DELETE_OPERATION = "Exclui uma categoria de cliente existente";
	public static final String CATEGORIA_CLIENTE_DELETE_RESPONSE = "Indica que a categoria de cliente foi excluída. Nenhum conteúdo será retornado";

	public static final String CIDADE_LIST_OPERATION = "Obtém uma lista de cidades";
	public static final String CIDADE_LIST_RESPONSE = "Retorna todas as cidades";
	public static final String CIDADE_GET_OPERATION = "Obtém os dados de uma cidade específica";
	public static final String CIDADE_GET_RESPONSE = "Retorna a cidade correspondente ao cidadeUid informado";
	public static final String CIDADE_CREATE_OPERATION = "Cadastra uma nova cidade";
	public static final String CIDADE_CREATE_RESPONSE = "Retorna a nova cidade cadastrada";
	public static final String CIDADE_UPDATE_OPERATION = "Atualiza os dados de uma cidade existente";
	public static final String CIDADE_UPDATE_RESPONSE = "Retorna a cidade atualizada";
	public static final String CIDADE_DELETE_OPERATION = "Exclui uma cidade existente";
	public static final String CIDADE_DELETE_RESPONSE = "Indica que a cidade foi excluída. Nenhum conteúdo será retornado";

	public static final String CLIENTE_LIST_OPERATION = "Obtém uma lista de clientes, conforme configuração nos parâmetros de request";
	public static final String CLIENTE_LIST_OPERATION_DESCRIPTION = "Valor default dos parâmetros de request: page=1 | size=25 | sort=id"
			+ "<br>Valores alternativos aceitos nos parâmetros de request: size={1..100} | sort={codigo, nome, razaoSocial}"
			+ "<br>Filtro pelos atributos do cliente (codigo, ativo, nomeOuRazaoSocial) serão aplicados quando o parâmetro correspondente for informado:"
			+ "<br>- codigo= retorna apenas o cliente com o código informado"
			+ "<br>- ativo= retorna apenas os clientes com a configuração informada (ativo={true | false})"
			+ "<br>- nomeOuRazaoSocial= retorna clientes cujo Nome ou Razão Social contenha o texto informado"
			+ "<br>O response indica as configurações de paginação utilizadas nos headers: pagination-page-number, pagination-page-size, pagination-total-pages, pagination-total-items";
	public static final String CLIENTE_LIST_RESPONSE = "Retorna a {page} requisitada com {size} clientes ordenados por {id} que atendam aos parâmetros informados";
	public static final String CLIENTE_GET_OPERATION = "Obtém os dados de um cliente específico";
	public static final String CLIENTE_GET_RESPONSE = "Retorna o cliente correspondente ao clienteUid informado";
	public static final String CLIENTE_CREATE_OPERATION = "Cadastra um novo cliente";
	public static final String CLIENTE_CREATE_RESPONSE = "Retorna o novo cliente cadastrado";
	public static final String CLIENTE_UPDATEALL_OPERATION = "Atualiza os dados de um cliente existente";
	public static final String CLIENTE_UPDATEALL_OPERATION_DESCRIPTION = "Atualiza todos os atributos do cliente com os valores informados no payload. Se o valor do payload for vazio ou null para um determinado atributo, o conteúdo do atributo será apagado";
			//"Update all Cliente's fields with the payload values. If the payload value is empty or null, the field's value will be erased";
	public static final String CLIENTE_UPDATEALL_RESPONSE = "Retorna o cliente atualizado";
	public static final String CLIENTE_UPDATENOTNULL_OPERATION = "Atualiza os dados contidos no payload para um cliente existente";
	public static final String CLIENTE_UPDATENOTNULL_OPERATION_DESCRIPTION = "Atualiza um atributo do cliente apenas quando um valor não nulo for informado no payload para este atributo. Caso contrário, o conteúdo do atributo não será alterado.";
			//"Update each Cliente's field only when the related payload field has a meaningful value (is not null). Otherwise, the field value will not be changed";
	public static final String CLIENTE_UPDATENOTNULL_RESPONSE = "Retorna o cliente atualizado";
	public static final String CLIENTE_DELETE_OPERATION = "Exclui um cliente existente";
	public static final String CLIENTE_DELETE_RESPONSE = "Indica que o cliente foi excluído. Nenhum conteúdo será retornado";

	public static final String TIPO_TELEFONE_LIST_OPERATION = "Obtém uma lista de tipos de telefone";
	public static final String TIPO_TELEFONE_LIST_RESPONSE = "Retorna todos os tipos de telefone";
	public static final String TIPO_TELEFONE_GET_OPERATION = "Obtém os dados de um tipo de telefone específico";
	public static final String TIPO_TELEFONE_GET_RESPONSE = "Retorna o tipo de telefone correspondente ao tipoTelefoneUid informado";
	public static final String TIPO_TELEFONE_CREATE_OPERATION = "Cadastra um novo tipo de telefone";
	public static final String TIPO_TELEFONE_CREATE_RESPONSE = "Retorna o novo tipo de telefone cadastrado";
	public static final String TIPO_TELEFONE_UPDATE_OPERATION = "Atualiza os dados de um tipo de telefone existente";
	public static final String TIPO_TELEFONE_UPDATE_RESPONSE = "Retorna o tipo de telefone atualizado";
	public static final String TIPO_TELEFONE_DELETE_OPERATION = "Exclui um tipo de telefone existente";
	public static final String TIPO_TELEFONE_DELETE_RESPONSE = "Indica que o tipo de telefone foi excluído. Nenhum conteúdo será retornado";

}
