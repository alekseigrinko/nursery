package service;

import model.Animal;
import model.AnimalSpecie;
import model.Specie;

import java.util.Arrays;

public class CreateBDService {
    private ConnectionService connectionService;
    private AnimalService animalService;

    public CreateBDService(ConnectionService connectionService, AnimalService animalService) {
        this.connectionService = connectionService;
        this.animalService = animalService;
    }

    public void addBD() {
        connectionService.workInBD("DROP DATABASE IF EXISTS friends_person;");
        connectionService.workInBD("CREATE DATABASE IF NOT EXISTS friends_person;");
        connectionService.putInfo("DROP TABLE IF EXISTS animal_species;");
        connectionService.putInfo("CREATE TABLE animal_species" +
                "(animal_species_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(512) NOT NULL, " +
                "parent_id BIGINT NOT NULL" +
                ");");
        connectionService.putInfo("DROP TABLE IF EXISTS animals;");
        connectionService.putInfo("CREATE TABLE animals (\n" +
                "animal_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "specie_id BIGINT NOT null,\n" +
                "FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS pets;");
        connectionService.putInfo("CREATE TABLE pets (\n" +
                "pet_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT null,\n" +
                "specie_id BIGINT NOT null,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS pack_animals;");
        connectionService.putInfo("CREATE TABLE pack_animals (\n" +
                "pack_animal_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT null,\n" +
                "specie_id BIGINT NOT null,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS cats;");
        connectionService.putInfo("CREATE TABLE cats (\n" +
                "cat_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "pet_id BIGINT NOT NULL,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (pet_id) REFERENCES pets(pet_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS dogs;");
        connectionService.putInfo("CREATE TABLE dogs (\n" +
                "dogs_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "pet_id BIGINT NOT NULL,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (pet_id) REFERENCES pets(pet_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS hamsters;");
        connectionService.putInfo("CREATE TABLE hamsters (\n" +
                "hamster_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "pet_id BIGINT NOT NULL,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (pet_id) REFERENCES pets(pet_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS horses;");
        connectionService.putInfo("CREATE TABLE horses (\n" +
                "horse_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "pack_animal_id BIGINT NOT NULL,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (pack_animal_id) REFERENCES pack_animals(pack_animal_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS donkeys;");
        connectionService.putInfo("CREATE TABLE donkeys (\n" +
                "donkey_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "pack_animal_id BIGINT NOT NULL,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (pack_animal_id) REFERENCES pack_animals(pack_animal_id));");
        connectionService.putInfo("DROP TABLE IF EXISTS camels;");
        connectionService.putInfo("CREATE TABLE camels (\n" +
                "camel_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "pack_animal_id BIGINT NOT NULL,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (pack_animal_id) REFERENCES pack_animals(pack_animal_id));");
        animalService.setCount(0);
    }

    public void insertSpecie() {
        for (Specie specie : Specie.values()) {
            connectionService.putInfo("INSERT INTO animal_species (name, parent_id) VALUES " +
                    "('" + specie.toString() + "', '" + getParent(specie) + "');");
        }
    }

    private AnimalSpecie selectSpecieToName(String name) {
        String request = connectionService.
                getInfo("select * from animal_species where name = '" + name + "';", 3);
        String[] elements = request.split(" :: ");
        int parent_id = 0;
        if (elements.length > 2) {
            parent_id = Integer.parseInt(elements[2]);
        }
        return new AnimalSpecie(Integer.parseInt(elements[0]), Specie.valueOf(elements[1]), parent_id);
    }

    private String selectSpecieToAnimalId(int animalId) {
        return connectionService.
                getInfo("select name from animal_species \n" +
                        "join animals on animal_species_id = animals.specie_id \n" +
                        "where animals.animal_id  = " + animalId + ";", 1);
    }

    private int getParent(Specie specie) {
        if (specie.equals(Specie.PET) || specie.equals(Specie.PACK_ANIMAL)) {
            return selectSpecieToName(Specie.ANIMAL.toString()).getAnimalSpeciesId();
        } else if (specie.equals(Specie.CAT) || specie.equals(Specie.DOG) || specie.equals(Specie.HAMSTER)) {
            return selectSpecieToName(Specie.PET.toString()).getAnimalSpeciesId();
        } else if (specie.equals(Specie.HORSE) || specie.equals(Specie.CAMEL) || specie.equals(Specie.DONKEY)) {
            return selectSpecieToName(Specie.PACK_ANIMAL.toString()).getAnimalSpeciesId();
        } else {
            return 0;
        }
    }

    private String insertAnimal(int specieId) {
        connectionService.putInfo("INSERT INTO animals (specie_id) VALUES " +
                "('" + specieId + "');");
        return connectionService.getInfo("SELECT max(animal_id) from animals", 1);
    }

    private String insertPet(String animalId, String specieId) {
        connectionService.putInfo("INSERT INTO pets (animal_id, specie_id) VALUES " +
                "('" + animalId + "', '" + specieId + "');");
        return connectionService.getInfo("SELECT pet_id from pets where animal_id = " + animalId + ";", 1);
    }

    private String insertPackAnimal(String animalId, String specieId) {
        connectionService.putInfo("INSERT INTO pack_animals (animal_id, specie_id) VALUES " +
                "('" + animalId + "', '" + specieId + "');");
        return connectionService.getInfo("SELECT pack_animal_id from pack_animals where animal_id = " + animalId + ";", 1);
    }

    private void insertCat() {
        String specieId = String.valueOf(selectSpecieToName("CAT").getAnimalSpeciesId());
        String animalId = insertAnimal(selectSpecieToName("CAT").getAnimalSpeciesId());
        String petId = insertPet(animalId, specieId);
        String commands = animalService.chooseRandom(animalService.getCommandsPet()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPet()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPet());
        connectionService.putInfo("INSERT INTO cats (animal_id, pet_id, name, commands, birthday) VALUES " +
                "('" + animalId + "', '" + petId + "', '" + animalService.chooseRandom(animalService.getNamesPet())
                + "', '" + commands + "', '" + animalService.generationDate() + "');");
    }

    private void insertDog() {
        String specieId = String.valueOf(selectSpecieToName("DOG").getAnimalSpeciesId());
        String animalId = insertAnimal(selectSpecieToName("DOG").getAnimalSpeciesId());
        String petId = insertPet(animalId, specieId);
        String commands = animalService.chooseRandom(animalService.getCommandsPet()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPet()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPet());
        connectionService.putInfo("INSERT INTO dogs (animal_id, pet_id, name, commands, birthday) VALUES " +
                "('" + animalId + "', '" + petId + "', '" + animalService.chooseRandom(animalService.getNamesPet())
                + "', '" + commands + "', '" + animalService.generationDate() + "');");
    }

    private void insertHamster() {
        String specieId = String.valueOf(selectSpecieToName("HAMSTER").getAnimalSpeciesId());
        String animalId = insertAnimal(selectSpecieToName("HAMSTER").getAnimalSpeciesId());
        String petId = insertPet(animalId, specieId);
        String commands = animalService.chooseRandom(animalService.getCommandsPet()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPet()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPet());
        connectionService.putInfo("INSERT INTO hamsters (animal_id, pet_id, name, commands, birthday) VALUES " +
                "('" + animalId + "', '" + petId + "', '" + animalService.chooseRandom(animalService.getNamesPet())
                + "', '" + commands + "', '" + animalService.generationDate() + "');");
    }

    private void insertHorse() {
        String specieId = String.valueOf(selectSpecieToName("HORSE").getAnimalSpeciesId());
        String animalId = insertAnimal(selectSpecieToName("HORSE").getAnimalSpeciesId());
        String packAnimalId = insertPackAnimal(animalId, specieId);
        String commands = animalService.chooseRandom(animalService.getCommandsPackAnimals()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPackAnimals()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPackAnimals());
        connectionService.putInfo("INSERT INTO horses (animal_id, pack_animal_id, name, commands, birthday) VALUES " +
                "('" + animalId + "', '" + packAnimalId + "', '" + animalService.chooseRandom(animalService.getNamesPackAnimals())
                + "', '" + commands + "', '" + animalService.generationDate() + "');");
    }

    private void insertDonkey() {
        String specieId = String.valueOf(selectSpecieToName("DONKEY").getAnimalSpeciesId());
        String animalId = insertAnimal(selectSpecieToName("DONKEY").getAnimalSpeciesId());
        String packAnimalId = insertPackAnimal(animalId, specieId);
        String commands = animalService.chooseRandom(animalService.getCommandsPackAnimals()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPackAnimals()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPackAnimals());
        connectionService.putInfo("INSERT INTO donkeys (animal_id, pack_animal_id, name, commands, birthday) VALUES " +
                "('" + animalId + "', '" + packAnimalId + "', '" + animalService.chooseRandom(animalService.getNamesPackAnimals())
                + "', '" + commands + "', '" + animalService.generationDate() + "');");
    }

    private void insertCamel() {
        String specieId = String.valueOf(selectSpecieToName("CAMEL").getAnimalSpeciesId());
        String animalId = insertAnimal(selectSpecieToName("CAMEL").getAnimalSpeciesId());
        String packAnimalId = insertPackAnimal(animalId, specieId);
        String commands = animalService.chooseRandom(animalService.getCommandsPackAnimals()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPackAnimals()) + ", " +
                animalService.chooseRandom(animalService.getCommandsPackAnimals());
        connectionService.putInfo("INSERT INTO camels (animal_id, pack_animal_id, name, commands, birthday) VALUES " +
                "('" + animalId + "', '" + packAnimalId + "', '" + animalService.chooseRandom(animalService.getNamesPackAnimals())
                + "', '" + commands + "', '" + animalService.generationDate() + "');");
    }

    public void insertPets(int cats, int dogs, int hamsters) {
        while (cats > 0) {
            insertCat();
            animalService.setCount(animalService.getCount() + 1);
            --cats;
        }
        while (dogs > 0) {
            insertDog();
            animalService.setCount(animalService.getCount() + 1);

            --dogs;
        }
        while (hamsters > 0) {
            insertHamster();
            animalService.setCount(animalService.getCount() + 1);
            --hamsters;
        }
    }

    public void insertPackAnimals(int horses, int donkeys, int camels) {
        while (horses > 0) {
            insertHorse();
            animalService.setCount(animalService.getCount() + 1);
            --horses;
        }

        while (donkeys > 0) {
            insertDonkey();
            animalService.setCount(animalService.getCount() + 1);
            --donkeys;
        }

        while (camels > 0) {
            insertCamel();
            animalService.setCount(animalService.getCount() + 1);
            --camels;
        }
    }

    public String selectAnimal(int animalId) {
        String nameSpecie = selectSpecieToAnimalId(animalId);
        String response = "";
        switch (nameSpecie) {
            case "CAT":
                response = connectionService.
                        getInfo("select * from cats \n" +
                                "where animal_id  = " + animalId + ";", 6);
                break;
            case "DOG":
                response = connectionService.
                        getInfo("select * from dogs \n" +
                                "where animal_id  = " + animalId + ";", 6);
                break;
            case "HAMSTER":
                response = connectionService.
                        getInfo("select * from hamsters \n" +
                                "where animal_id  = " + animalId + ";", 6);
                break;
            case "HORSE":
                response = connectionService.
                        getInfo("select * from horses \n" +
                                "where animal_id  = " + animalId + ";", 6);
                break;
            case "CAMEL":
                response = connectionService.
                        getInfo("select * from camels \n" +
                                "where animal_id  = " + animalId + ";", 6);
                break;
            case "DONKEY":
                response = connectionService.
                        getInfo("select * from donkeys \n" +
                                "where animal_id  = " + animalId + ";", 6);
                break;
            default:
                response = "0 :: 0 :: 0 :: null :: null :: 1900-01-01 00:00:00";
                break;
        }
        return response;
    }

    public void updateCommandsAnimal(int animalId, String commands) {
        String nameSpecie = selectSpecieToAnimalId(animalId);
        switch (nameSpecie) {
            case "CAT":
                connectionService.
                        putInfo("update cats set commands = '" + commands +
                                "' where animal_id  = " + animalId + ";");
                break;
            case "DOG":
                connectionService.
                        putInfo("update dogs set commands = '" + commands +
                                "' where animal_id  = " + animalId + ";");
                break;
            case "HAMSTER":
                connectionService.
                        putInfo("update hamsters set commands = '" + commands +
                                "' where animal_id  = " + animalId + ";");
                break;
            case "HORSE":
                connectionService.
                        putInfo("update horses set commands = '" + commands +
                                "' where animal_id  = " + animalId + ";");
                break;
            case "CAMEL":
                connectionService.
                        putInfo("update camels set commands = '" + commands +
                                "' where animal_id  = " + animalId + ";");
                break;
            case "DONKEY":
                connectionService.
                        putInfo("update donkeys set commands = '" + commands +
                                "' where animal_id = " + animalId + ";");
                break;
            default:
                System.out.println("Животное не найдено!");
                break;
        }
    }

    public void deleteTable(String nameSpecie) {
        int specieID = selectSpecieToName(nameSpecie).getAnimalSpeciesId();
        if (nameSpecie.equals("CAT") || nameSpecie.equals("DOG") || nameSpecie.equals("HAMSTER")) {
            switch (nameSpecie) {
                case "CAT" -> connectionService.putInfo("delete from cats;");
                case "DOG" -> connectionService.putInfo("delete from dogs;");
                case "HAMSTER" -> connectionService.putInfo("delete from hamsters;");
            }
            connectionService.putInfo("delete from pets where specie_id = " + specieID + ";");
            connectionService.putInfo("delete from animals where specie_id = " + specieID + ";");
        } else if (nameSpecie.equals("HORSE") || nameSpecie.equals("CAMEL") || nameSpecie.equals("DONKEY")) {
            switch (nameSpecie) {
                case "HORSE" -> connectionService.putInfo("delete from horses;");
                case "CAMEL" -> connectionService.putInfo("delete from camels;");
                case "DONKEY" -> connectionService.putInfo("delete from donkeys;");
            }
            connectionService.putInfo("delete from pack_animals where specie_id = " + specieID + ";");
            connectionService.putInfo("delete from animals where specie_id = " + specieID + ";");
        }
        animalService.setCount(Integer.parseInt(connectionService.
                getInfo("select count(*) from animals;", 1)));
    }

    public String selectTable(String nameSpecie) {
        String response = "";
        switch (nameSpecie) {
            case "CAT" -> response = connectionService.getInfo("select * from cats;", 6);
            case "DOG" -> response = connectionService.getInfo("select * from dogs;", 6);
            case "HAMSTER" -> response = connectionService.getInfo("select * from hamsters;", 6);
            case "HORSE" -> response = connectionService.getInfo("select * from horses;", 6);
            case "CAMEL" -> response = connectionService.getInfo("select * from camels;", 6);
            case "DONKEY" -> response = connectionService.getInfo("select * from donkeys;", 6);
        }
        return response;
    }

    public void createYoungTable() {
        connectionService.putInfo("DROP TABLE IF EXISTS young_animals;");
        connectionService.putInfo("CREATE TABLE young_animals (\n" +
                "young_animal_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "animal_id BIGINT NOT NULL,\n" +
                "specie_id BIGINT NOT null,\n" +
                "name VARCHAR(512) NOT NULL,\n" +
                "commands VARCHAR(512) NOT NULL,\n" +
                "birthday DATETIME NOT NULL,\n" +
                "FOREIGN KEY (animal_id) REFERENCES animals(animal_id),\n" +
                "FOREIGN KEY (specie_id) REFERENCES animal_species(animal_species_id));");
    }

    private String selectSpecieIdToAnimalId(int animalId) {
        return connectionService.
                getInfo("select specie_id from animals \n" +
                        "where animal_id  = " + animalId + ";", 1);
    }

    public Animal chooseAnimal(int animalId) {
        String animalInfo = selectAnimal(animalId);
        if (animalInfo.isBlank()) {
            animalInfo = "0 :: 0 :: 0 :: null :: null :: 1900-01-01 00:00:00";
        }
        String specieId = selectSpecieIdToAnimalId(animalId);
        if (specieId.isBlank()) {
            specieId = "0";
        }
        return animalService.chooseAnimal(animalInfo, Integer.parseInt(specieId));
    }

    public void deleteAnimal(int animalId) {
        String nameSpecie = selectSpecieToAnimalId(animalId);
        if (nameSpecie.equals("CAT") || nameSpecie.equals("DOG") || nameSpecie.equals("HAMSTER")) {
            switch (nameSpecie) {
                case "CAT" -> connectionService.putInfo("delete from cats where animal_id = " + animalId + ";");
                case "DOG" -> connectionService.putInfo("delete from dogs where animal_id = " + animalId + ";");
                case "HAMSTER" -> connectionService.putInfo("delete from hamsters where animal_id = " + animalId + ";");
            }
            connectionService.putInfo("delete from pets where animal_id = " + animalId + ";");
            connectionService.putInfo("delete from animals where animal_id = " + animalId + ";");
        } else if (nameSpecie.equals("HORSE") || nameSpecie.equals("CAMEL") || nameSpecie.equals("DONKEY")) {
            switch (nameSpecie) {
                case "HORSE" -> connectionService.putInfo("delete from horses where animal_id = " + animalId + ";");
                case "CAMEL" -> connectionService.putInfo("delete from camels where animal_id = " + animalId + ";");
                case "DONKEY" -> connectionService.putInfo("delete from donkeys where animal_id = " + animalId + ";");
            }
            connectionService.putInfo("delete from pack_animals where where animal_id = " + animalId + ";");
            connectionService.putInfo("delete from animals where where animal_id = " + animalId + ";");
        }
        animalService.setCount(Integer.parseInt(connectionService.
                getInfo("select count(*) from animals;", 1)));
    }

    public void replacePetsToDate() {
        createYoungTable();
        connectionService.putInfo("INSERT INTO young_animals (animal_id, specie_id, name, commands, birthday)  \n" +
                "SELECT cats.animal_id, animals.specie_id, name, commands, birthday FROM cats\n" +
                "left join animals on cats.animal_id  = animals.animal_id \n" +
                "where TIMESTAMPDIFF(month , birthday , now()) between 12 and 36;");
        connectionService.putInfo("INSERT INTO young_animals (animal_id, specie_id, name, commands, birthday)  \n" +
                "SELECT dogs.animal_id, animals.specie_id, name, commands, birthday FROM dogs\n" +
                "left join animals on dogs.animal_id  = animals.animal_id \n" +
                "where TIMESTAMPDIFF(month , birthday , now()) between 12 and 36;");
        connectionService.putInfo("INSERT INTO young_animals (animal_id, specie_id, name, commands, birthday)  \n" +
                "SELECT hamsters.animal_id, animals.specie_id, name, commands, birthday FROM hamsters\n" +
                "left join animals on hamsters.animal_id  = animals.animal_id \n" +
                "where TIMESTAMPDIFF(month , birthday , now()) between 12 and 36;");
        connectionService.putInfo("INSERT INTO young_animals (animal_id, specie_id, name, commands, birthday)  \n" +
                "SELECT horses.animal_id, animals.specie_id, name, commands, birthday FROM horses\n" +
                "left join animals on horses.animal_id  = animals.animal_id \n" +
                "where TIMESTAMPDIFF(month , birthday , now()) between 12 and 36;");
        connectionService.putInfo("INSERT INTO young_animals (animal_id, specie_id, name, commands, birthday)  \n" +
                "SELECT camels.animal_id, animals.specie_id, name, commands, birthday FROM camels\n" +
                "left join animals on camels.animal_id  = animals.animal_id \n" +
                "where TIMESTAMPDIFF(month , birthday , now()) between 12 and 36;");
        connectionService.putInfo("INSERT INTO young_animals (animal_id, specie_id, name, commands, birthday)  \n" +
                "SELECT donkeys.animal_id, animals.specie_id, name, commands, birthday FROM donkeys\n" +
                "left join animals on donkeys.animal_id  = animals.animal_id \n" +
                "where TIMESTAMPDIFF(month , birthday , now()) between 12 and 36;");


        // раскомментировать, если после переноса необходимо удалить данные с старых таблиц
        //String[] elements = connectionService.getInfo("select animal_id from young_animals;", 1).split("\n");
        //Arrays.stream(elements).map(Integer::parseInt).forEach(this::deleteAnimalsAfterReplace);
    }

    private void deleteAnimalsAfterReplace(int animalId) {
        String nameSpecie = selectSpecieToAnimalId(animalId);
        switch (nameSpecie) {
            case "CAT" -> connectionService.putInfo("delete from cats where animal_id = " + animalId + ";");
            case "DOG" -> connectionService.putInfo("delete from dogs where animal_id = " + animalId + ";");
            case "HAMSTER" -> connectionService.putInfo("delete from hamsters where animal_id = " + animalId + ";");
            case "HORSE" -> connectionService.putInfo("delete from horses where animal_id = " + animalId + ";");
            case "CAMEL" -> connectionService.putInfo("delete from camels where animal_id = " + animalId + ";");
            case "DONKEY" -> connectionService.putInfo("delete from donkeys where animal_id = " + animalId + ";");
        }
    }
}
