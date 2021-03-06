-- Felipe Henrique Moura
CREATE TABLE users(
	id SERIAL primary key,
	name VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL
);
-- Ruben de Pontes Gomes
CREATE TABLE contact(
	id SERIAL PRIMARY KEY,
	id_target int REFERENCES users(id) NOT NULL,
	id_owner int REFERENCES users(id) NOT NULL
);
-- Ruben de Pontes Gomes
CREATE TABLE messages(
	id SERIAL PRIMARY KEY,
	id_src INT REFERENCES users(id) NOT NULL,
	id_dst INT REFERENCES users(id) NOT NULL,
	date_sended TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	msg TEXT NOT NULL
);
-- Victor Cintra
CREATE TABLE stories(
	id SERIAL PRIMARY KEY,
	id_owner INT REFERENCES users(id) NOT NULL,
	message TEXT NOT NULL,
	post_date DATE NOT NULL DEFAULT CURRENT_DATE
);