CREATE TABLE users(
	id SERIAL primary key,
	name VARCHAR(255) NOT NULL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	ip_address VARCHAR(11)
);
CREATE TABLE contact(
	id SERIAL PRIMARY KEY,
	id_target int REFERENCES users(id) NOT NULL,
	id_owner int REFERENCES users(id) NOT NULL
);
CREATE TABLE messages(
	id SERIAL PRIMARY KEY,
	id_src INT REFERENCES users(id) NOT NULL,
	id_dst INT REFERENCES users(id) NOT NULL,
	date_creation DATE NOT NULL,
	data TEXT NOT NULL
);
CREATE TABLE stories(
	id SERIAL PRIMARY KEY,
	id_owner INT REFERENCES users(id) NOT NULL,
	message TEXT NOT NULL
);