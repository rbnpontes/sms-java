-- Ruben de Pontes Gomes
CREATE OR REPLACE FUNCTION clearOutdatedStories() RETURNS TRIGGER AS $$
BEGIN
	DELETE FROM stories WHERE post_date != CURRENT_DATE;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER CleanOutdatedStories
AFTER INSERT ON contact
EXECUTE PROCEDURE clearOutdatedStories()