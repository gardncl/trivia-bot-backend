CREATE TABLE asked_question_ledger {
    id SERIAL PRIMARY KEY,
    question INTEGER REFERENCES question_answer_pairs(id) NOT NULL,
    user INTEGER REFERENCES slack_users(id) NOT NULL,
    asked_at DATE NOT NULL,
        UNIQUE(question, user)
};