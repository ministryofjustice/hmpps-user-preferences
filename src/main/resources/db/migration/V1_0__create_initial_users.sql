create extension if not exists hstore;
create table if not exists users (
    id uuid not null unique,
    properties hstore,
    primary key (id)
);