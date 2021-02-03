create table users (
   id uuid not null unique,
    courts text[],
    primary key (id)
);