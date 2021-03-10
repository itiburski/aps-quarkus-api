CREATE TABLE CLIENTE_REPLICA (
  ID                              BIGINT,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  ATIVO                           BOOLEAN,
  CONSTRAINT CLIENTE_REPLICA_PK PRIMARY KEY (ID),
  CONSTRAINT CLIENTE_REPLICA_UID_UK UNIQUE (UID)
);
