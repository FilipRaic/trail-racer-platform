CREATE TABLE IF NOT EXISTS race
(
    id                  UUID PRIMARY KEY,
    name                VARCHAR(255)                NOT NULL,
    start_date_time_utc TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    distance            VARCHAR(255)                NOT NULL,
    version             BIGINT                      NOT NULL
);

CREATE INDEX IF NOT EXISTS ix_race_name ON race (name);
CREATE INDEX IF NOT EXISTS ix_race_start_date_time_utc ON race (start_date_time_utc);
