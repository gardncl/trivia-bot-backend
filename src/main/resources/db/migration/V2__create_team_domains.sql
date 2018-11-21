CREATE TABLE team_domains (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
        UNIQUE(name)
);