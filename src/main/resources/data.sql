INSERT INTO users (user_id, first_name, last_name, email, password, phone, enabled, revoked, revoked_timestamp,
                   created_timestamp, authorities)
VALUES ('2d16fac9-5dea-4995-b921-b2ec47d0fa04', 'Admin', 'Admin', 'admin@test.com',
        '$2a$10$U5Oz1fzpRWHhdbMabPboxez2seTtM1V3X76nY.hXFSzKP9y6YTG5a', '', true, false, NULL, '2024-03-27T00:00:00Z',
        '{"ADMIN"}')
ON CONFLICT DO NOTHING;

INSERT INTO users (user_id, first_name, last_name, email, password, phone, enabled, revoked, revoked_timestamp,
                   created_timestamp, authorities)
VALUES ('646f1901-4c3e-400f-9320-f6743373f46b', 'User', 'User', 'user@test.com',
        '$2a$10$U5Oz1fzpRWHhdbMabPboxez2seTtM1V3X76nY.hXFSzKP9y6YTG5a', '', true, false, NULL, '2024-03-27T00:00:00Z',
        '{"USER"}')
ON CONFLICT DO NOTHING;

INSERT INTO users (user_id, first_name, last_name, email, password, phone, enabled, revoked, revoked_timestamp,
                   created_timestamp, authorities)
VALUES ('279d6e4a-f604-4ea5-bb27-1ca48fccb4ab', 'User', 'User', 'user@testing.com',
        '$2a$10$U5Oz1fzpRWHhdbMabPboxez2seTtM1V3X76nY.hXFSzKP9y6YTG5a', '', true, false, NULL, '2024-03-27T00:00:00Z',
        '{"USER"}')
ON CONFLICT DO NOTHING;

-- Diagnostics
INSERT INTO diagnostics (diagnostic_id, diagnostic_description, diagnostic_name)
VALUES ('85a8e9c8-2dd3-4e39-b0be-0099430c444a', 'Тест на самооценку (Тест Сон-Райз)', 'Son-Rise Test')
ON CONFLICT DO NOTHING;

-- Questions
INSERT INTO questions (question_id, question_text, diagnostic_id)
VALUES ('94cc2d22-b301-4ee2-8988-a76215738067', 'Как вы оцениваете свои способности?',
        '85a8e9c8-2dd3-4e39-b0be-0099430c444a'),
       ('94cc2d22-b301-4ee2-8988-a76215738068', 'Как вы оцениваете свою самоэффективность?',
        '85a8e9c8-2dd3-4e39-b0be-0099430c444a')
ON CONFLICT DO NOTHING;

-- Answers
INSERT INTO answers (answer_id, answer_text, interpretation_points, scale_points, question_id)
VALUES ('94cc2d22-b301-4ee2-8988-a76215738067', 'Совершенно не согласен', 0, 0, '94cc2d22-b301-4ee2-8988-a76215738067'),
       ('94cc2d22-b301-4ee2-8988-a76215738068', 'Скорее не согласен', 0, 1, '94cc2d22-b301-4ee2-8988-a76215738067'),
       ('94cc2d22-b301-4ee2-8988-a76215738069', 'Не знаю', 1, 2, '94cc2d22-b301-4ee2-8988-a76215738067'),
       ('94cc2d22-b301-4ee2-8988-a7621573806a', 'Скорее согласен', 2, 0, '94cc2d22-b301-4ee2-8988-a76215738067'),
       ('94cc2d22-b301-4ee2-8988-a7621573806b', 'Совершенно согласен', 2, 1, '94cc2d22-b301-4ee2-8988-a76215738067'),

       ('94cc2d22-b301-4ee2-8988-a7621573806c', 'Совершенно не согласен', 0, 0, '94cc2d22-b301-4ee2-8988-a76215738068'),
       ('94cc2d22-b301-4ee2-8988-a7621573806d', 'Скорее не согласен', 0, 1, '94cc2d22-b301-4ee2-8988-a76215738068'),
       ('94cc2d22-b301-4ee2-8988-a7621573806e', 'Не знаю', 1, 2, '94cc2d22-b301-4ee2-8988-a76215738068'),
       ('94cc2d22-b301-4ee2-8988-a7621573806f', 'Скорее согласен', 2, 0, '94cc2d22-b301-4ee2-8988-a76215738068'),
       ('94cc2d22-b301-4ee2-8988-a76215738070', 'Совершенно согласен', 2, 1, '94cc2d22-b301-4ee2-8988-a76215738068')
ON CONFLICT DO NOTHING;

