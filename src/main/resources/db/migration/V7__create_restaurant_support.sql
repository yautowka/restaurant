CREATE TABLE restaurant_support
(
    id            INT PRIMARY key AUTO_INCREMENT,
    support_id    INT NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY (support_id) REFERENCES users (id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
);