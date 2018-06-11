CREATE VIEW StatisticLog AS
SELECT
COUNT(DISTINCT contact.id) / 2 AS ContatosArmazenados,
COUNT(DISTINCT users.id) AS UsuariosCadastrados,
COUNT(DISTINCT messages.id) AS MensagensEnviadas,
COUNT(DISTINCT stories.id) AS StoriesCriadas
FROM contact
INNER JOIN users ON users.id = users.id
INNER JOIN messages ON messages.id = messages.id
INNER JOIN stories ON stories.id = stories.id
-- For Select VIEW Uncomment query below
-- SELECT * FROM StatisticLog