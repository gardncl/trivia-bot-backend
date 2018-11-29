CREATE TABLE asked_question_ledger (
    id SERIAL PRIMARY KEY,
    question_id INTEGER NOT NULL,
    user_id TEXT NOT NULL,
    asked_at DATE NOT NULL,
    FOREIGN KEY (question_id) REFERENCES question_answer_pairs(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES slack_users(id) ON DELETE CASCADE
);