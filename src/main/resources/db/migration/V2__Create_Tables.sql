create schema if not exists tipos;
create schema if not exists entidades;

create table if not exists tipos.permissoes (
    id serial primary key,
    nome varchar not null
);

create table if not exists entidades.usuarios (
    id serial primary key,
    permissao_id integer references tipos.permissoes not null,
    usuario varchar not null,
    senha varchar not null
);

create table if not exists tipos.categorias (
    id serial primary key,
    nome varchar not null
);

create table if not exists entidades.produtos (
    id serial primary key,
    categoria_id integer references tipos.categorias not null,
    nome varchar not null,
    quantidade integer not null,
    descricao varchar null
);



