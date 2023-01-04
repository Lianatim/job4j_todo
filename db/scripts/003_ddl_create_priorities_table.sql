CREATE TABLE priorities
(
    id       SERIAL PRIMARY KEY,
    name     TEXT UNIQUE NOT NULL,
    position int
);

comment on table priorities is 'Таблица с приоритетами';
comment on column priorities.id is 'Идентификатор приоритета';
comment on column priorities.name is 'Название приоритета';
comment on column priorities.position is 'Очередность приоритета';