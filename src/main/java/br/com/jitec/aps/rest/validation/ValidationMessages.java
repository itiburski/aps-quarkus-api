package br.com.jitec.aps.rest.validation;

public abstract class ValidationMessages {

	public static final String REQUEST_BODY_NOT_NULL = "Request body deve ser informado";

	public static final String DESCRICAO_NOT_BLANK = "descricao deve ser informada";
	public static final String NOME_NOT_BLANK = "nome deve ser informado";
	public static final String UF_NOT_BLANK = "uf deve ser informada";
	public static final String RAZAO_SOCIAL_NOT_BLANK = "razaoSocial deve ser informado";

	public static final String NOME_SIZE = "nome deve ter no máximo 60 caracteres";
	public static final String UF_SIZE = "uf deve ter 2 caracteres";
	public static final String RAZAO_SOCIAL_SIZE = "razaoSocial deve ter no máximo 60 caracteres";

	public static final String CONTATO_SIZE = "contato deve ter no máximo 60 caracteres";
	public static final String RUA_SIZE = "rua deve ter no máximo 60 caracteres";
	public static final String COMPLEMENTO_SIZE = "complemento deve ter no máximo 30 caracteres";
	public static final String BAIRRO_SIZE = "bairro deve ter no máximo 40 caracteres";
	public static final String CEP_SIZE = "cep deve ter no máximo 8 caracteres";
	public static final String HOMEPAGE_SIZE = "homepage deve ter no máximo 80 caracteres";
	public static final String CNPJ_SIZE = "cnpj deve ter no máximo 14 caracteres";
	public static final String IE_SIZE = "inscricaoEstadual deve ter no máximo 20 caracteres";

	public static final String CEP_FORMAT = "cep deve conter somente numeros";
	public static final String CNPJ_FORMAT = "cnpj deve conter somente numeros";

}
