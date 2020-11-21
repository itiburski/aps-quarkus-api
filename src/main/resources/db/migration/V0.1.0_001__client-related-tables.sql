
CREATE TABLE CATEGORIA_CLIENTE (
  ID                              BIGINT ,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  DESCRICAO                       CHARACTER VARYING(40),
  CONSTRAINT CATEGORIA_CLIENTE_PK PRIMARY KEY (ID),
  CONSTRAINT CATEGORIA_CLIENTE_UID_UK UNIQUE (UID)
);

CREATE TABLE CIDADE (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  NOME                            CHARACTER VARYING(60),
  UF                              CHARACTER VARYING(2),
  CONSTRAINT CIDADE_PK PRIMARY KEY (ID),
  CONSTRAINT CIDADE_UID_UK UNIQUE (UID)
);

CREATE TABLE CLIENTE (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  CODIGO                          INTEGER Not Null,
  NOME                            CHARACTER VARYING(60) Not Null,
  RAZAO                           CHARACTER VARYING(60) Not Null,
  SALDO                           NUMERIC(14, 4) Not Null,
  ATIVO                           BOOLEAN,
  CONTATO                         CHARACTER VARYING(60),
  RUA                             CHARACTER VARYING(60),
  COMPL                           CHARACTER VARYING(30),
  BAIRRO                          CHARACTER VARYING(40),
  CEP                             CHARACTER VARYING(8),
  HOMEPAGE                        CHARACTER VARYING(80),
  CNPJ                            CHARACTER VARYING(14),
  IE                              CHARACTER VARYING(20),
  CIDADE_ID                       BIGINT,
  CATEGORIA_CLIENTE_ID            BIGINT,
  CONSTRAINT CLIENTE_PK PRIMARY KEY (ID),
  CONSTRAINT CLIENTE_UID_UK UNIQUE (UID),
  CONSTRAINT CLIENTE_CODIGO_UK UNIQUE (CODIGO),
  CONSTRAINT CLIENTE_CIDADE_FK FOREIGN KEY (CIDADE_ID) REFERENCES CIDADE (ID),
  CONSTRAINT CLIENTE_CATEGORIA_CLIENTE_FK FOREIGN KEY (CATEGORIA_CLIENTE_ID) REFERENCES CATEGORIA_CLIENTE (ID)
);


CREATE TABLE CLIENTE_EMAIL (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  CLIENTE_ID                      BIGINT Not Null,
  EMAIL                           CHARACTER VARYING(80),
  CONSTRAINT CLIENTE_EMAIL_PK PRIMARY KEY (ID),
  CONSTRAINT CLIENTE_EMAIL_UID_UK UNIQUE (UID),
  CONSTRAINT CLIENTE_EMAIL_CLIENTE_FK FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID)
);

CREATE TABLE TIPO_FONE (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  DESCRICAO                       CHARACTER VARYING(40),
  CONSTRAINT TIPO_FONE_PK PRIMARY KEY (ID),
  CONSTRAINT TIPO_FONE_UID_UK UNIQUE (UID)
);

CREATE TABLE CLIENTE_FONE (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  CLIENTE_ID                      BIGINT Not Null,
  TIPO_FONE_ID                    BIGINT,
  NUMERO                          CHARACTER VARYING(11),
  CONSTRAINT CLIENTE_FONE_PK PRIMARY KEY (ID),
  CONSTRAINT CLIENTE_FONE_UID_UK UNIQUE (UID),
  CONSTRAINT CLIENTE_FONE_CLIENTE_FK FOREIGN KEY (CLIENTE_ID) REFERENCES CLIENTE (ID),
  CONSTRAINT CLIENTE_FONE_TIPO_FONE_FK FOREIGN KEY (TIPO_FONE_ID) REFERENCES TIPO_FONE (ID)
);
