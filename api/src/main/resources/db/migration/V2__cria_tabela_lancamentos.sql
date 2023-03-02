CREATE TABLE financas.lancamento
(
  id bigserial NOT NULL PRIMARY KEY ,
  descricao character varying(100) NOT NULL,
  mes integer NOT NULL,
  ano integer NOT NULL,
  valor numeric(16,2) NOT NULL,
  tipo character varying(20) CHECK ( tipo in ('RECEITA', 'DESPESA')),
  status character varying(20) CHECK ( status IN ('PENDENTE', 'CANCELADO', 'EFETIVADO')) NOT NULL,
  id_usuario bigint REFERENCES financas.usuario (id),
  data_cadastro date default now()
);