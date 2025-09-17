ALTER TABLE users
DROP COLUMN last_logout_timestamp;

ALTER TABLE verification_tokens
DROP COLUMN expiry_time;