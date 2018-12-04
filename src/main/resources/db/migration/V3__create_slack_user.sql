CREATE TABLE slack_users (
    id TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    team_domain_id TEXT REFERENCES team_domains(id),
        UNIQUE(name, team_domain_id)
);