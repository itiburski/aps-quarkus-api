package br.com.jitec.aps.rest.validation;

public abstract class ValidationMessages {

	public static final String REQUEST_BODY_NOT_NULL = "Request body deve ser informado";

	public static final String DESCRICAO_NOT_BLANK = "descricao deve ser informada";
	public static final String NOME_NOT_BLANK = "nome deve ser informado";
	public static final String UF_NOT_BLANK = "uf deve ser informada";

	public static final String NOME_SIZE = "nome deve ter no máximo 60 caracteres";
	public static final String UF_SIZE = "uf deve ter 2 caracteres";

}
