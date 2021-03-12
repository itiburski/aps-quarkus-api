CREATE TABLE FATURA (
  ID                              BIGINT Not Null,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  CODIGO                          INTEGER,
  DATA                            TIMESTAMP,
  VALOR_TOTAL                     NUMERIC(14, 4),
  CONSTRAINT FATURA_PK PRIMARY KEY (ID),
  CONSTRAINT FATURA_UID_UK UNIQUE (UID),
  CONSTRAINT FATURA_CODIGO_UK UNIQUE (CODIGO)
);
