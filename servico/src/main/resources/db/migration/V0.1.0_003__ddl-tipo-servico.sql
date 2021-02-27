CREATE TABLE TIPO_SERVICO (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  NOME                            CHARACTER VARYING(40),
  CONSTRAINT TIPO_SERVICO_PK PRIMARY KEY (ID),
  CONSTRAINT TIPO_SERVICO_UID_UK UNIQUE (UID)
);
