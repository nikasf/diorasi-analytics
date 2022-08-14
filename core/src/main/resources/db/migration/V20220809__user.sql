CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE app_user(
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


CREATE TABLE website (
    id UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    owner_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    domain VARCHAR(500) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_user
      FOREIGN KEY(owner_id) 
	  REFERENCES app_user(id)
	  ON DELETE CASCADE

);

INSERT INTO public.app_user(username, "password", email, enabled, created_at, last_login, "role", name)
VALUES('admin', 'admin', 'user@diorasi.gr', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN', 'Sofia Nk');
