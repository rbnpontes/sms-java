-- Ruben de Pontes Gomes
-- $1 = userID
-- Fetch all Contacts from Selected userID
CREATE OR REPLACE FUNCTION GetUserContacts(integer) 
RETURNS TABLE (
	id int,
	IdContato int,
	Nome varchar(255),
	Usuario varchar(255)
) AS $func$
BEGIN
		RETURN QUERY
		SELECT
		contact.id AS id,
		target.id AS IdContato,
		target.name AS Nome, 
		target.username AS Usuario
		FROM contact
		INNER JOIN users AS target ON target.id = contact.id_target 
		INNER JOIN users AS owner ON owner.id = contact.id_owner
		WHERE
		owner.id = $1;
END;
$func$ LANGUAGE plpgsql;
-- SELECT * FROM GetUserContacts(2)