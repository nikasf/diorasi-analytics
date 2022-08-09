CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users(
	id         UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
	username   VARCHAR(255),
	password   VARCHAR(255),
	email      VARCHAR(255),
	enabled    BOOLEAN            NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
	last_login TIMESTAMP,
	role       VARCHAR(255),	
	name       VARCHAR(255)
);