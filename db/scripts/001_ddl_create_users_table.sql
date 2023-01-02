CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

comment on table users is 'Таблица с пользователями';
comment on column users.id is 'Имя пользователя';
comment on column users.login is 'Логин пользователя';
comment on column users.password is 'Пароль пользователя';