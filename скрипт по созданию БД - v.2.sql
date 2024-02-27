DROP DATABASE IF EXISTS friends_person;
CREATE DATABASE IF NOT EXISTS friends_person;
USE friends_person;

DROP TABLE IF EXISTS animal_species;
CREATE TABLE animal_species (
animal_species_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
name VARCHAR(512) NOT NULL, 
parent_id BIGINT NOT NULL);

INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('1', 'ANIMAL', '0');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('2', 'PET', '1');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('3', 'PACK_ANIMAL', '1');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('4', 'DOG', '2');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('5', 'CAT', '2');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('6', 'HAMSTER', '2');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('7', 'HORSE', '3');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('8', 'CAMEL', '3');
INSERT INTO animal_species (animal_species_id, name, parent_id) VALUES ('9', 'DONKEY', '3');

DROP TABLE IF EXISTS animals;
CREATE TABLE animals (
animal_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
specie_id BIGINT NOT null,
FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));

DROP TABLE IF EXISTS pets;
CREATE TABLE pets (
pet_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT null, 
specie_id BIGINT NOT null,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));

DROP TABLE IF EXISTS pack_animals;
CREATE TABLE pack_animals (
pack_animal_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT null,
specie_id BIGINT NOT null,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));

DROP TABLE IF EXISTS cats;
CREATE TABLE cats (
cat_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
pet_id BIGINT NOT NULL,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (pet_id) REFERENCES pets(pet_id));

DROP TABLE IF EXISTS dogs;
CREATE TABLE dogs (
dogs_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
pet_id BIGINT NOT NULL,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (pet_id) REFERENCES pets(pet_id));

DROP TABLE IF EXISTS hamsters;
CREATE TABLE hamsters (
hamster_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
pet_id BIGINT NOT NULL,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (pet_id) REFERENCES pets(pet_id));

DROP TABLE IF EXISTS horses;
CREATE TABLE horses (
horse_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
pack_animal_id BIGINT NOT NULL,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (pack_animal_id) REFERENCES pack_animals(pack_animal_id));

DROP TABLE IF EXISTS donkeys;
CREATE TABLE donkeys (
donkey_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
pack_animal_id BIGINT NOT NULL,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (pack_animal_id) REFERENCES pack_animals(pack_animal_id));

DROP TABLE IF EXISTS camels;
CREATE TABLE camels (
camel_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
pack_animal_id BIGINT NOT NULL,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (pack_animal_id) REFERENCES pack_animals(pack_animal_id));

DROP TABLE IF EXISTS young_animals;
CREATE TABLE young_animals (
young_animal_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
specie_id BIGINT NOT null,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT NULL,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));

DROP TABLE IF EXISTS combine_table;
CREATE TABLE combine_table (
combine_table_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
animal_id BIGINT NOT NULL,
specie_id BIGINT NOT null,
name VARCHAR(512) NOT NULL,
commands VARCHAR(512) NOT null,
birthday DATETIME NOT NULL,
FOREIGN KEY (animal_id) REFERENCES animals(animal_id),
FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));
