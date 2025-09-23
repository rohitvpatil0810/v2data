ALTER TABLE interactions
ADD COLUMN interaction_status TEXT NOT NULL DEFAULT 'IN_QUEUE',
ADD COLUMN updated_at timestamptz NOT NULL DEFAULT NOW();
