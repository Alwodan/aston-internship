# Little Overview

## Relations
One-to-Many: from Weapon to GameCharacter - many characters can have one type of weapon  
Many-to-Many: from GameCharacters to Factions - characters may join any number of factions

## How does it work? / Changes
Simple ReST api. Not like previous versions - now it works with request-bodies, not request-params.  
To specify entity's id just go with traditional /{id}.
  
But adding a faction to a character goes somewhat as usual - go for /characters/{id} and specify request param factionId.  

Yeah, naming changed from word_word to wordWord.

## How to start it?
gradle task appStart in gretty folder. When finished - gradle task appStop.

## Why no tests?
I think I will update it after some time.

# Known issues:
This version has zero DTOs and no Service-layer, since I thought they aren't really necessary here.  
It will be added in spring-boot version, but, if required, I can update this version.
