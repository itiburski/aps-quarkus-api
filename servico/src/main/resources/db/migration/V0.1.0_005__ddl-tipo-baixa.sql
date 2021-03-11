CREATE TABLE TIPO_BAIXA (
  ID                              BIGINT Not Null,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  NOME                            CHARACTER VARYING(30),
  CONSTRAINT TIPO_BAIXA_PK PRIMARY KEY (ID),
  CONSTRAINT TIPO_BAIXA_UID_UK UNIQUE (UID)
);
