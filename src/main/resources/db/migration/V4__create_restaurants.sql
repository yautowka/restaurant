DROP DATABASE IF EXISTS restaurants;
CREATE TABLE restaurants
(
    id            INT PRIMARY key AUTO_INCREMENT,
    name          varchar(255) NOT NULL,
    owner_id      int          NOT NULL,
    is_active     bit(1)       NOT NULL DEFAULT 0,
    opening_hours varchar(255) NOT NULL,
    city          varchar(255) NOT NULL,
    address       varchar(255) NOT NULL,
    lat           double       NOT NULL,
    lng           double       NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
);