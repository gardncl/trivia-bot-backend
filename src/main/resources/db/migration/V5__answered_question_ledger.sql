CREATE TABLE answered_question_ledger {
    id SERIAL PRIMARY KEY,
    asked_question INTEGER REFERENCES asked_question_ledger(id) NOT NULL,
    answer TEXT NOT NULL,
    correct BOOLEAN NOT NULL,
    responded_at DATE NOT NULL
}