package service;

import model.Animal;
import model.pet.Pet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalService {
    private int count;
    private List<String> commandsPet;
    private List<String> namesPet;
    private List<String> namesPackAnimals;
    private List<String> commandsPackAnimals;
    private DateTimeFormatter formatter;

    public AnimalService() {
        this.count = 0;
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:s");
        createdLists();
    }

    public LocalDate generationDate() {
        LocalDate startDate = LocalDate.of(2017, 1, 1);
        long start = startDate.toEpochDay();

        LocalDate endDate = LocalDate.now();
        long end = endDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    private void createdLists() {
        String[] namesForPets = {"Пушок", "Барсик", "Васька", "Рыжик", "Мурзик", "Кузя", "Боня", "Альфа", "Найда", "Джек", "Граф", "Арчи"};
        namesPet = Arrays.stream(namesForPets).toList();
        String[] commandsForPets = {"Лежать", "Сидеть", "Бегом", "Есть", "Спать", "Грызть", "Пить", "Жди", "Фу", "Нельзя", "Прыжок", "Голос"};
        commandsPet = Arrays.stream(commandsForPets).toList();
        String[] namesForAnimals = {"Ворон", "Вишня", "Кирпич", "Фигаро", "Грив", "Рафаэль", "Карпаччо", "Рихтер", "Бетховен", "Юстин", "Бонд"};
        namesPackAnimals = Arrays.stream(namesForAnimals).toList();
        String[] commandsForAnimals = {"Хоп", "Шагом", "Тише", "Стой", "Бегом", "Гит", "Цок-Цок", "Кушать", "Спать", "Лежать", "Тпру"};
        commandsPackAnimals = Arrays.stream(commandsForAnimals).toList();
    }

    public String chooseRandom(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    private Pet createPet() {
        List<String> commands = new ArrayList<>();
        commands.add(chooseRandom(commandsPet));
        commands.add(chooseRandom(commandsPet));
        commands.add(chooseRandom(commandsPet));
        return new Pet(0, 2, chooseRandom(namesPet), commands, generationDate());
    }

    public Animal chooseAnimal(String animalInfo, int specieId) {
        System.out.println(animalInfo);
        Animal animal = new Animal();
        String[] elements = animalInfo.split(" :: ");
        animal.setAnimalId(Integer.parseInt(elements[2]));

        animal.setSpecieId(specieId);
        animal.setName(elements[3]);

        String[] commandElements = elements[4].split(", ");
        List<String> commands = Arrays.stream(commandElements).toList();
        animal.setCommands(commands);

        animal.setBirthday(LocalDate.parse(elements[5], formatter));
        return animal;
    }

    public Animal add(String name, int specieId, List<String> commands, String birthday) {
        Animal animal = new Animal();
        try {
            if (!name.isBlank()) {
                animal.setName(name);
            } else {
                throw new Exception("Имя не может быть пустым!");
            }
            if (commands.size() > 0) {
                animal.setCommands(commands);
            } else {
                throw new Exception("Список команд не может быть пустым!");
            }
            animal.setSpecieId(specieId);
            try {
                animal.setBirthday(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            } catch (Exception e) {
                System.out.println("Ошибка формата ввода даты!");
            }
        } catch (Exception e) {
            System.out.println("Ошибка внесения данных животного:" + e.getMessage());
        }
        return animal;
    }

    public List<String> getCommandsPet() {
        return commandsPet;
    }

    public List<String> getNamesPet() {
        return namesPet;
    }

    public List<String> getNamesPackAnimals() {
        return namesPackAnimals;
    }

    public List<String> getCommandsPackAnimals() {
        return commandsPackAnimals;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
