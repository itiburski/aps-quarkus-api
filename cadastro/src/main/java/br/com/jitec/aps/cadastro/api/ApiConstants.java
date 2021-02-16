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

	public static final String CATEGORIA_CLIENTE_LIST_OPERATION = "Retorna todas as categorias de cliente";
	public static final String CATEGORIA_CLIENTE_LIST_RESPONSE = "Retorna todas as categorias de cliente";
	public static final String CATEGORIA_CLIENTE_GET_OPERATION = "Retorna os dados de uma categoria de cliente específica";
	public static final String CATEGORIA_CLIENTE_GET_RESPONSE = "Retorna a categoria de cliente correspondente ao categoriaClienteUid informado";
	public static final String CATEGORIA_CLIENTE_CREATE_OPERATION = "Cadastra uma nova categoria de cliente";
	public static final String CATEGORIA_CLIENTE_CREATE_RESPONSE = "Retorna a nova categoria de cliente cadastrada";
	public static final String CATEGORIA_CLIENTE_UPDATE_OPERATION = "Atualiza os dados de uma categoria de cliente existente";
	public static final String CATEGORIA_CLIENTE_UPDATE_RESPONSE = "Retorna a categoria de cliente atualizada";
	public static final String CATEGORIA_CLIENTE_DELETE_OPERATION = "Exclui uma categoria de cliente existente";
	public static final String CATEGORIA_CLIENTE_DELETE_RESPONSE = "Indica que a categoria de cliente foi excluída. Nenhum conteúdo será retornado";

	public static final String CIDADE_LIST_OPERATION = "Retorna todas as cidades";
	public static final String CIDADE_LIST_RESPONSE = "Retorna todas as cidades";
	public static final String CIDADE_GET_OPERATION = "Retorna os dados de uma cidade específica";
	public static final String CIDADE_GET_RESPONSE = "Retorna a cidade correspondente ao cidadeUid informado";
	public static final String CIDADE_CREATE_OPERATION = "Cadastra uma nova cidade";
	public static final String CIDADE_CREATE_RESPONSE = "Retorna a nova cidade cadastrada";
	public static final String CIDADE_UPDATE_OPERATION = "Atualiza os dados de uma cidade existente";
	public static final String CIDADE_UPDATE_RESPONSE = "Retorna a cidade atualizada";
	public static final String CIDADE_DELETE_OPERATION = "Exclui uma cidade existente";
	public static final String CIDADE_DELETE_RESPONSE = "Indica que a cidade foi excluída. Nenhum conteúdo será retornado";

	public static final String TIPO_TELEFONE_LIST_OPERATION = "Retorna todos os tipos de telefone";
	public static final String TIPO_TELEFONE_LIST_RESPONSE = "Retorna todos os tipos de telefone";
	public static final String TIPO_TELEFONE_GET_OPERATION = "Retorna os dados de um tipo de telefone específico";
	public static final String TIPO_TELEFONE_GET_RESPONSE = "Retorna o tipo de telefone correspondente ao tipoTelefoneUid informado";
	public static final String TIPO_TELEFONE_CREATE_OPERATION = "Cadastra um novo tipo de telefone";
	public static final String TIPO_TELEFONE_CREATE_RESPONSE = "Retorna o novo tipo de telefone cadastrado";
	public static final String TIPO_TELEFONE_UPDATE_OPERATION = "Atualiza os dados de um tipo de telefone existente";
	public static final String TIPO_TELEFONE_UPDATE_RESPONSE = "Retorna o tipo de telefone atualizado";
	public static final String TIPO_TELEFONE_DELETE_OPERATION = "Exclui um tipo de telefone existente";
	public static final String TIPO_TELEFONE_DELETE_RESPONSE = "Indica que o tipo de telefone foi excluído. Nenhum conteúdo será retornado";

}
