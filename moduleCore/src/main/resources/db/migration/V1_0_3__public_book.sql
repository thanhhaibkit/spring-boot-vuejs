drop table if exists book;
create table book
(
    id              bigserial                   not null,
    created_by      integer,
    created_at      timestamp   default now()   not null,
    updated_by      integer,
    updated_at      timestamp,
    version         integer     default 1       not null,
    title           varchar                     not null,
    author          varchar                     not null
);
