CREATE TABLE IF NOT EXISTS answers
(
    answer_id             UUID NOT NULL,
    answer_text           VARCHAR(255),
    interpretation_points SMALLINT,
    scale_points          SMALLINT,
    question_id           UUID,
    CONSTRAINT answers_pkey PRIMARY KEY (answer_id)
);

CREATE TABLE IF NOT EXISTS diagnostic_results
(
    diagnostic_result_id  UUID NOT NULL,
    interpretation_points SMALLINT,
    scale_points          SMALLINT,
    diagnostic_id         UUID,
    user_id               UUID,
    CONSTRAINT diagnostic_results_pkey PRIMARY KEY (diagnostic_result_id)
);

CREATE TABLE IF NOT EXISTS diagnostics
(
    diagnostic_id          UUID NOT NULL,
    diagnostic_description VARCHAR(255),
    diagnostic_name        VARCHAR(255),
    CONSTRAINT diagnostics_pkey PRIMARY KEY (diagnostic_id)
);

CREATE TABLE IF NOT EXISTS images
(
    image_id          UUID NOT NULL,
    content           BYTEA,
    content_type      VARCHAR(255),
    created_timestamp TIMESTAMP,
    name              VARCHAR(255),
    size              BIGINT,
    CONSTRAINT images_pkey PRIMARY KEY (image_id)
);

CREATE TABLE IF NOT EXISTS interpretations
(
    interpretation_id   UUID NOT NULL,
    interpretation_text VARCHAR(255),
    CONSTRAINT interpretations_pkey PRIMARY KEY (interpretation_id)
);

CREATE TABLE IF NOT EXISTS mak_card_images
(
    mac_card_id UUID NOT NULL,
    image_id    UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS mak_cards
(
    mak_card_id UUID NOT NULL,
    description VARCHAR(255),
    image_url   VARCHAR(255),
    name        VARCHAR(255),
    image_id    UUID,
    user_id     UUID,
    CONSTRAINT mak_cards_pkey PRIMARY KEY (mak_card_id)
);

CREATE TABLE IF NOT EXISTS questions
(
    question_id   UUID NOT NULL,
    question_text VARCHAR(255),
    diagnostic_id UUID,
    CONSTRAINT questions_pkey PRIMARY KEY (question_id)
);

CREATE TABLE IF NOT EXISTS scales
(
    scale_id   UUID NOT NULL,
    scale_name VARCHAR(255),
    CONSTRAINT scales_pkey PRIMARY KEY (scale_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id           UUID NOT NULL,
    authorities       VARCHAR(255)[],
    created_timestamp TIMESTAMP,
    email             VARCHAR(255),
    enabled           BOOLEAN,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    password          VARCHAR(255),
    phone             VARCHAR(255),
    revoked           BOOLEAN,
    revoked_timestamp TIMESTAMP,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);