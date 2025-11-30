CREATE TABLE IF NOT EXISTS application
(
    id      UUID PRIMARY KEY,
    user_id BIGINT NOT NULL,
    club    VARCHAR(255),
    race_id UUID   NOT NULL,
    version BIGINT NOT NULL
);

CREATE INDEX IF NOT EXISTS ix_application_user ON application (user_id);
CREATE INDEX IF NOT EXISTS ix_application_race ON application (race_id);
