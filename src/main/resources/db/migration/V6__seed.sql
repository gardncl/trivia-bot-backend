INSERT INTO team_domains(id, name)
VALUES ('abcd1234', 'dont-tread-on-this');

INSERT INTO slack_users(id, name, team_domain_id)
VALUES ('ghjk', 'clay', 'abcd1234');

INSERT INTO question_answer_pairs(question, answer)
VALUES ('What is the wing span of a 747 aircraft?', '196 feet');

INSERT INTO asked_question_ledger(question_id, user_id, asked_at)
VALUES (1, 'ghjk', '2018-11-26');

INSERT INTO answered_question_ledger(asked_question_id, answer, correct, responded_at)
VALUES (1, '215 feet', 'false', '2018-11-27');

