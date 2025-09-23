ALTER TABLE interactions RENAME TO notes;

ALTER TABLE notes
RENAME COLUMN interaction_status TO notes_status