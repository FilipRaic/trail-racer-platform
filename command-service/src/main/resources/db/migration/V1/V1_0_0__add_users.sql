CREATE SEQUENCE IF NOT EXISTS seq_app_user_generator START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS app_user
(
    id            BIGINT PRIMARY KEY DEFAULT NEXTVAL('seq_app_user_generator'),
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255) NOT NULL,
    date_of_birth DATE         NOT NULL,
    email         VARCHAR(255) NOT NULL,
    role          VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    CONSTRAINT uk_user_email UNIQUE (email)
);

CREATE SEQUENCE IF NOT EXISTS seq_refresh_token_generator START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS refresh_token
(
    id          BIGINT PRIMARY KEY,
    token       VARCHAR(255) NOT NULL,
    user_id     BIGINT       NOT NULL,
    expiry_date TIMESTAMP    NOT NULL,
    CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE CASCADE,
    CONSTRAINT uk_refresh_token_token UNIQUE (token)
);

CREATE INDEX IF NOT EXISTS ix_users_email ON app_user (email);
CREATE INDEX IF NOT EXISTS ix_refresh_token_token ON refresh_token (token);
