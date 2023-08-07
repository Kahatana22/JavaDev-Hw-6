CREATE TABLE worker
(
    ID       SERIAL PRIMARY KEY,
    NAME     varchar(1000) NOT NULL,
    BIRTHDAY DATE,
    level TEXT NOT NULL,
    SALARY   INT
);

CREATE TABLE client (
    ID       SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL CHECK (LENGTH(name) BETWEEN 2 AND 1000)
);

CREATE TABLE project (
    ID       SERIAL PRIMARY KEY,
    client_id BIGINT,
    start_date DATE,
    finish_date DATE,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE project_worker (
    project_id BIGINT,
    worker_id BIGINT,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id)
);