CREATE TABLE IF NOT EXISTS answers
(
    answer_id             uuid NOT NULL,
    answer_text           character varying(255),
    interpretation_points smallint,
    scale_points          smallint,
    question_id           uuid,
    CONSTRAINT answers_pkey PRIMARY KEY (answer_id)
);

CREATE TABLE IF NOT EXISTS diagnostic_results
(
    diagnostic_result_id  uuid NOT NULL,
    interpretation_points smallint,
    scale_points          smallint,
    diagnostic_id         uuid,
    user_id               uuid,
    CONSTRAINT diagnostic_results_pkey PRIMARY KEY (diagnostic_result_id)
);

CREATE TABLE IF NOT EXISTS diagnostics
(
    diagnostic_id          uuid NOT NULL,
    diagnostic_description character varying(255),
    diagnostic_name        character varying(255),
    CONSTRAINT diagnostics_pkey PRIMARY KEY (diagnostic_id)
);

CREATE TABLE IF NOT EXISTS images
(
    image_id          uuid NOT NULL,
    content           bytea,
    content_type      character varying(255),
    created_timestamp timestamp(6) with time zone,
    name              character varying(255),
    size              bigint,
    CONSTRAINT images_pkey PRIMARY KEY (image_id)
);

CREATE TABLE IF NOT EXISTS interpretations
(
    interpretation_id   uuid NOT NULL,
    interpretation_text character varying(255),
    CONSTRAINT interpretations_pkey PRIMARY KEY (interpretation_id)
);

CREATE TABLE IF NOT EXISTS mak_card_images
(
    mac_card_id uuid NOT NULL,
    image_id    uuid NOT NULL
);

CREATE TABLE IF NOT EXISTS mak_cards
(
    mak_card_id uuid NOT NULL,
    description character varying(255),
    image_url   character varying(255),
    name        character varying(255),
    image_id    uuid,
    user_id     uuid,
    CONSTRAINT mak_cards_pkey PRIMARY KEY (mak_card_id)
);

CREATE TABLE IF NOT EXISTS questions
(
    question_id   uuid NOT NULL,
    question_text character varying(255),
    diagnostic_id uuid,
    CONSTRAINT questions_pkey PRIMARY KEY (question_id)
);

CREATE TABLE IF NOT EXISTS scales
(
    scale_id   uuid NOT NULL,
    scale_name character varying(255),
    CONSTRAINT scales_pkey PRIMARY KEY (scale_id)
);

CREATE TABLE IF NOT EXISTS users
(
    user_id           uuid NOT NULL,
    authorities       character varying(255)[],
    created_timestamp timestamp(6) with time zone,
    email             character varying(255),
    enabled           boolean,
    first_name        character varying(255),
    last_name         character varying(255),
    password          character varying(255),
    phone             character varying(255),
    revoked           boolean,
    revoked_timestamp timestamp(6) with time zone,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);