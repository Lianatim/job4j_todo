CREATE TABLE IF NOT EXISTS categories
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

comment on table categories is 'Таблица с категориями';
comment on column categories.id is 'Уникальный id категории';
comment on column categories.name is 'Имя категории';