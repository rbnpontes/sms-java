-- Felipe Henrique Moura
-- Select user by ID
SELECT * FROM users WHERE id={IdUsuario};
-- Select all Users from DB
SELECT * FROM users;
-- Get Amount of User registred
SELECT COUNT(id) FROM users;
-- Ruben de Pontes Gomes
-- Select all messages 
SELECT * FROM messages;
-- Get Count of all messages sended
SELECT COUNT(id) FROM messages;
-- Victor Cintra
-- Select Current Stories
SELECT * FROM stories WHERE post_date = CURRENT_DATE;
-- Get count of all stories posted in current day
SELECT COUNT(id) stories WHERE post_date = CURRENT_DATE;