ALTER TABLE interactions
ALTER COLUMN created_at
TYPE timestamptz
USING created_at AT TIME ZONE 'Asia/Kolkata';

ALTER TABLE files
ALTER COLUMN created_at
TYPE timestamptz
USING created_at AT TIME ZONE 'Asia/Kolkata';

ALTER TABLE verification_tokens
ALTER COLUMN created_at
TYPE timestamptz
USING created_at AT TIME ZONE 'Asia/Kolkata';


ALTER TABLE users
ALTER COLUMN created_at
TYPE timestamptz
USING created_at AT TIME ZONE 'Asia/Kolkata';