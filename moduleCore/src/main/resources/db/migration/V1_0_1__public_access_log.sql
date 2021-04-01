drop table if exists access_log;
create table access_log
(
    id               bigserial               not null,
    created_by       integer,
    created_at       timestamp default now() not null,
    updated_by       integer,
    updated_at       timestamp,
    version          integer    default 1     not null,
    method           varchar(7)              not null,
    uri              varchar                 not null,
    status           integer                 not null,
    remote_address   varchar                 not null,
    user_agent       varchar,
    principal        varchar,
    session_id       varchar,
    duration         time,
    request_headers  varchar,
    request_body     varchar,
    response_headers varchar,
    response_body    varchar
);

comment on table access_log is 'アクセスログ';
comment on column access_log.method is 'メソッド';
comment on column access_log.uri is 'URI';
comment on column access_log.status is 'ステータス';
comment on column access_log.remote_address is 'アドレス';
comment on column access_log.user_agent is 'ユーザーエージェント';
comment on column access_log.principal is 'プリンシパル';
comment on column access_log.session_id is 'セッションID';
comment on column access_log.duration is '経過時間';
comment on column access_log.request_headers is 'リクエストヘッダ';
comment on column access_log.request_body is 'リクエストボディ';
comment on column access_log.response_headers is 'レスポンスヘッダ';
comment on column access_log.response_body is 'レスポンスボディ';
