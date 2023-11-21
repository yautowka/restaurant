CREATE TABLE user_token
(
    id                      INT PRIMARY key,
    user_id                 Int          NOT NULL,
    access_token            VARCHAR(255) NOT NULL,
    refresh_token           VARCHAR(255) NOT NULL,
    access_token_expires_at int          not null,
    FOREIGN KEY (user_id) REFERENCES user(id)
);