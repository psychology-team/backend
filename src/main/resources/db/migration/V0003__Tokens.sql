CREATE TABLE IF NOT EXISTS tokens
(
    token_id          UUID NOT NULL,
    token             VARCHAR(255),
    revoked           BOOLEAN,
    expired           BOOLEAN,
    user_id           UUID,
    updated_timestamp TIMESTAMPTZ,
    CONSTRAINT tokens_pkey PRIMARY KEY (token_id)
);