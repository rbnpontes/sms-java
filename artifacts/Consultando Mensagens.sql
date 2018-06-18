-- Felipe Henrique Moura
-- $1 = userID = User was Started Conversation
-- $2 = targetID = User was Received Conversation
-- *Important, doenst import order of params, because this query
-- will give a ordered messages and don't give priority for first or 
-- second parameter, its only for make a relationship
CREATE OR REPLACE FUNCTION ReadMessages(integer, integer) 
RETURNS TABLE (
	id int,
	enviado timestamp,
	relacao text,
	mensagem text
) AS $func$
BEGIN
		RETURN QUERY
		SELECT 
		messages.id AS Id,
		messages.date_sended AS Enviado,
		CONCAT('(',src.username,') ',src.name,' -> (',dst.username,') ',dst.name) AS Relacao,
		messages.msg AS Mensagem
		FROM messages 
		JOIN users AS src ON messages.id_src = src.id
		JOIN users AS dst ON messages.id_dst = dst.id
		WHERE
		(id_src = $1 AND id_dst= $2)
		OR 
		(id_src = $2 AND id_dst= $1)
		ORDER BY date_sended ASC;
END;
$func$ LANGUAGE plpgsql;
-- Uncoment query below for execute this Function
--SELECT * FROM ReadMessages(2,4)