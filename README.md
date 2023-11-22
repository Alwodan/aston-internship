# Little Overview

## Entities

### GameCharacter
Fields: character_id, character_weapon_id, character_name, character_powerlevel  
Link for requests: localhost:8080/characters

### Weapon
Fields: weapon_id, weapon_name, weapon_damage  
Link for requests: localhost:8080/weapons

### Faction
Fields: faction_id, faction_name  
Link for requests: localhost:8080/factions

## Relations
One-to-Many: from Weapon to GameCharacter - many characters can have one type of weapon  
Many-to-Many: from Characters to Factions - characters may join any number of factions

## How does it work?
Service supports main HTTP-requests: GET, POST, PUT, DELETE.  
Specify parameters in url.  
You can GET any entity by ID by specifying it.  
To add or remove factions, specify both faction_id AND character_id, when working with /characters. Why so? I thought that only a character decides to join or to leave a faction, not the other way around.  
PUT request will update only specified fields.

## How to start it?
gradle task appStart in gretty folder. When finished - gradle task appStop.

## Why no tests?
I think I will update it after some time.
