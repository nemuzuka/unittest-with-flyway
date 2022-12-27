CREATE TABLE tasks (
    task_id VARCHAR (128) NOT NULL,
    task_code VARCHAR (128) NOT NULL,
    status VARCHAR (4) NOT NULL,
    title VARCHAR (256),
    content_value VARCHAR (1024),
    deadline BIGINT,
    attributes TEXT,
    create_user_code VARCHAR (128),
    create_at BIGINT,
    last_update_user_code VARCHAR (128),
    last_update_at BIGINT,
    version_no BIGINT
);

ALTER TABLE tasks ADD CONSTRAINT tasks_primary PRIMARY KEY(task_id);
CREATE UNIQUE INDEX ui_tasks_task_code ON tasks (task_code);
