INSERT  INTO users (user_id, first_name, last_name, email, password, phone, enabled, revoked, revoked_timestamp, created_timestamp, authorities)
VALUES ('2d16fac9-5dea-4995-b921-b2ec47d0fa04', 'Admin', 'Admin', 'admin@test.com', '$2a$10$U5Oz1fzpRWHhdbMabPboxez2seTtM1V3X76nY.hXFSzKP9y6YTG5a', '', true, false, NULL, '2024-03-27T00:00:00Z', '{"ADMIN"}')
ON CONFLICT DO NOTHING;

INSERT  INTO users (user_id, first_name, last_name, email, password, phone, enabled, revoked, revoked_timestamp, created_timestamp, authorities)
VALUES (gen_random_uuid(), 'User', 'User', 'user@test.com', '$2a$10$U5Oz1fzpRWHhdbMabPboxez2seTtM1V3X76nY.hXFSzKP9y6YTG5a', '', true, false, NULL, '2024-03-27T00:00:00Z', '{"USER"}')
ON CONFLICT DO NOTHING;

INSERT  INTO users (user_id, first_name, last_name, email, password, phone, enabled, revoked, revoked_timestamp, created_timestamp, authorities)
VALUES (gen_random_uuid(), 'User', 'User', 'user@testing.com', '$2a$10$U5Oz1fzpRWHhdbMabPboxez2seTtM1V3X76nY.hXFSzKP9y6YTG5a', '', true, false, NULL, '2024-03-27T00:00:00Z', '{"USER"}')
ON CONFLICT DO NOTHING;

