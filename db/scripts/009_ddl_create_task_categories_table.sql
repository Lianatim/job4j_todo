CREATE TABLE IF NOT EXISTS task_categories
(
    id SERIAL PRIMARY KEY,
    task_id int NOT NULL REFERENCES tasks(id),
    category_id int NOT NULL REFERENCES categories(id)
);

comment on table "task_categories" is 'Таблица задач с категориями';
comment on column "task_categories".id is 'Уникальный id категории';
comment on column "task_categories".task_id is 'Идентификатор задачи';
comment on column "task_categories".category_id is 'Идентификатор категории';