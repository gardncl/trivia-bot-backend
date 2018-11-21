CREATE TABLE question_answer_pairs (
  id SERIAL PRIMARY KEY,
  question TEXT NOT NULL,
  answer TEXT NOT NULL,
    UNIQUE(question)
);