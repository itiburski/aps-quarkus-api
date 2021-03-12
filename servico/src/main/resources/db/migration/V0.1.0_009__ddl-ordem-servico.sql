CREATE SEQUENCE ORDEM_SERVICO_NUMERO START 1;

CREATE TABLE ORDEM_SERVICO (
  ID                              BIGINT Not Null,
  UID                             UUID Not Null,
  VERSION                         INTEGER Not Null,
  NUMERO                          INTEGER,
  CLIENTE_REPLICA_ID              BIGINT,
  TIPO_SERVICO_ID                 BIGINT,
  VALOR                           NUMERIC(14, 4),
  CONTATO                         CHARACTER VARYING(60),
  DESCRICAO                       CHARACTER VARYING(100),
  OBSERVACAO                      CHARACTER VARYING(8192),
  DT_ENTRADA                      TIMESTAMPTZ,
  DT_AGENDA                       TIMESTAMPTZ,
  DT_CONCLUSAO                    TIMESTAMPTZ,
  DT_ENTREGA                      TIMESTAMPTZ,
  FATURA_ID                       BIGINT,
  CONSTRAINT ORDEM_SERVICO_PK PRIMARY KEY (ID),
  CONSTRAINT ORDEM_SERVICO_UID_UK UNIQUE (UID),
  CONSTRAINT ORDEM_SERVICO_NUMERO_UK UNIQUE (NUMERO),
  CONSTRAINT ORDEM_SERVICO_FATURA_FK FOREIGN KEY (FATURA_ID) REFERENCES FATURA (ID),
  CONSTRAINT ORDEM_SERVICO_CLIENTE_REPLICA_FK FOREIGN KEY (CLIENTE_REPLICA_ID) REFERENCES CLIENTE_REPLICA (ID),
  CONSTRAINT ORDEM_SERVICO_TIPO_SERVICO_FK FOREIGN KEY (TIPO_SERVICO_ID) REFERENCES TIPO_SERVICO (ID)
);
