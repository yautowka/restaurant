CREATE TABLE user
(
    id            INT PRIMARY key,
    login         Int          NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at    VARCHAR(255) NOT NULL,
    updated_at    int,
    last_login    int
        UNIQUE (login)
);
