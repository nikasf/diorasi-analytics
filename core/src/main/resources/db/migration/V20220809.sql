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
    id 			UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    owner_id 	UUID NOT NULL,
    name 		VARCHAR(100) NOT NULL,
    domain 		VARCHAR(500) NOT NULL,
    created_at 	TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_user
      FOREIGN KEY(owner_id) 
	  REFERENCES app_user(id)
	  ON DELETE CASCADE
);

CREATE TABLE event (
	id 				UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    website_id		UUID NOT NULL,
    session_id 		UUID NOT NULL,
    created_at 		TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    url 			VARCHAR(500) NOT NULL,
    event_type 		VARCHAR(50) NOT NULL,
    event_value 	VARCHAR(50),
    referrer 		VARCHAR(500),
    CONSTRAINT fk_website
      FOREIGN KEY(website_id) 
	  REFERENCES website(id)
	  ON DELETE CASCADE
);

CREATE TABLE session (
    id 				UUID PRIMARY KEY NOT NULL DEFAULT uuid_generate_v4(),
    website_id		UUID NOT NULL,
    created_at 		TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    browser 		VARCHAR(20),
    os 				VARCHAR(30),
    device 			VARCHAR(20),
    screen 			VARCHAR(11),
    language 		VARCHAR(35),
    country 		CHAR(2),
    CONSTRAINT fk_website
      FOREIGN KEY(website_id) 
	  REFERENCES website(id)
	  ON DELETE CASCADE
);

ALTER TABLE event
  ADD FOREIGN KEY (session_id) REFERENCES session (id);


CREATE INDEX event_createdDate_idx ON event (created_at);

INSERT INTO public.app_user(username, "password", email, enabled, created_at, last_login, "role", name)
VALUES('admin', 'admin', 'admin@diorasi.gr', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN', 'Admin admin');

INSERT INTO public.app_user(username, "password", email, enabled, created_at, last_login, "role", name)
VALUES('user', 'user', 'user@diorasi.gr', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'USER', 'User user');
