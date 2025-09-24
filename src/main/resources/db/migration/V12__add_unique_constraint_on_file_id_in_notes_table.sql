ALTER TABLE notes
ADD CONSTRAINT unique_file_id UNIQUE (file_id);
