create table if not exists users (
   id uuid not null unique,
    courts text[],
    primary key (id)
);