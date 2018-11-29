CREATE TABLE team_domains (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
        UNIQUE(name)
);