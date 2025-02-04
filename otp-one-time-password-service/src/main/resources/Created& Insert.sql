CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(255) NOT NULL,
    country VARCHAR(255),
    full_name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    PRIMARY KEY (username)
);

INSERT INTO users (username, country, full_name, password, role)
VALUES 
    ('admin', 'India', 'Admin -Jack', '$2a$10$Z5F2Elzpnwx6kp0CYLmdo.Tcv8SZWMANvlr/PWr6.IxWWXnAi7KNC', 'ROLE_ADMIN'),
    ('user', 'USA', 'User-Ruby', '$2a$10$KFsPE4H9buyijUht5nQU..qdpkDRA5q6zPeACtLVWAaDh3kMzPxXG', 'ROLE_USER');
