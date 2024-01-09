CREATE TABLE users
(
    id            INT PRIMARY key AUTO_INCREMENT,
    login         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at    datetime(6)  NOT NULL,
    updated_at    datetime(6),
    last_login    datetime(6),
    role          enum ('ROLE_ADMIN','ROLE_OWNER','ROLE_USER'),
    UNIQUE (login)
);