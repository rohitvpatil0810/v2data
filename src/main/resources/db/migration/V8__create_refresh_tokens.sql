CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    hashed_refresh_token VARCHAR(512) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at timestamptz DEFAULT NOW()
);
