CREATE TABLE weapon (
	weapon_id SERIAL PRIMARY KEY,
	weapon_name varchar(255),
	weapon_damage integer
);

CREATE TABLE gameCharacter (
	character_id SERIAL PRIMARY KEY,
	character_name varchar(255),
	character_powerlevel integer,
	character_weapon_id integer not null,
	FOREIGN KEY (character_weapon_id) REFERENCES weapon(weapon_id)
);

CREATE TABLE faction (
	faction_id SERIAL PRIMARY KEY,
	faction_name varchar(255),
	faction_credo varchar(255)
);

CREATE TABLE character_factions (
	char_id integer not null,
	fac_id integer not null,
	PRIMARY KEY (char_id, fac_id),
	FOREIGN KEY (char_id) REFERENCES gameCharacter(character_id),
	FOREIGN KEY (fac_id) REFERENCES faction(faction_id)
);