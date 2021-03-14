CREATE TABLE CLIENTE_REPLICA (
  ID                              BIGINT Not Null,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  NOME                            CHARACTER VARYING(60) Not Null,
  ATIVO                           BOOLEAN,
  CONSTRAINT CLIENTE_REPLICA_PK PRIMARY KEY (ID),
  CONSTRAINT CLIENTE_REPLICA_UID_UK UNIQUE (UID)
);
