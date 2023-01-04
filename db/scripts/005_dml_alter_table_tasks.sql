ALTER TABLE tasks
    ADD COLUMN IF NOT EXISTS priority_id int REFERENCES priorities (id);