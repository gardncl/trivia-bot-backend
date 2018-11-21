CREATE TABLE slack_users {
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    team_domain INTEGER REFERENCES team_domains(id),
        UNIQUE(name, team_domain)
};