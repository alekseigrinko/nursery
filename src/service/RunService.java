package service;

import java.util.Scanner;

public class RunService {
    private ConnectionService connectionService;
    private AnimalService animalService;
    private CreateBDService createBDService;
    Scanner scanner;

    public RunService() {
        this.connectionService = new ConnectionService();
        this.animalService = new AnimalService();
        this.createBDService = new CreateBDService(connectionService, animalService);
        this.scanner = new Scanner(System.in, "utf-8");
    }


    public void start() {
        while (true) {
            menu();
            int command = command(scanner);
            if (command == 1) {
                addBD();
            } else if (command == 2) {
                showTables();
            } else if (command == 3) {
                updateAnimalsTables();
            } else if (command == 4) {
                updateAnimal();
            } else if (command == 5) {
                clearTable();
            } else if (command == 6) {
                getAnimalCount();
            } else if (command == 7) {
                selectTable();
            } else if (command == 8) {
                chooseAnimal();
            } else if (command == 9) {
                replacePets();
            } else if (command == 0) {
                System.out.println("Выход из меню.");
                break;
            } else {
                System.out.println("Некорректная команда, попробуйте ещё раз.");
            }
        }
    }

    private void menu() {
        System.out.println("--------------------------------------------");
        System.out.println("Введите команду:");
        System.out.println("1 - Создать БД");
        System.out.println("2 - Показать таблицы");
        System.out.println("3 - Добавить в таблицы животных");
        System.out.println("4 - Обновить команды животного");
        System.out.println("5 - Очистить данные по таблице вида животных");
        System.out.println("6 - Показать счетчик животных");
        System.out.println("7 - Показать список животных");
        System.out.println("8 - Показать данные животного по ID");
        System.out.println("9 - Перенести животных старше года и моложе 3 лет в отдельную таблицу");
        System.out.println("0 - Выход");
        System.out.println("--------------------------------------------");
    }

    private int command(Scanner scanner) {
        int command;
        while (true) {
            if (scanner.hasNextInt()) {
                command = scanner.nextInt();
                break;
            } else {
                System.out.println("Некорректное значение, необходимо ввести число!");
                scanner.next();
            }
        }
        return command;
    }

    private void addBD() {
        createBDService.addBD();
        insertSpecies();
    }

    private void showTables() {
        System.out.println(connectionService.getInfo("SHOW TABLES;", 1));
    }

    private void insertSpecies() {
        createBDService.insertSpecie();
    }

    private void updatePets() {
        createBDService.insertPets(7, 7, 7);
    }

    private void updatePackAnimals() {
        createBDService.insertPackAnimals(7, 7, 7);
    }

    private void updateAnimalsTables() {
        System.out.println("Введите команду:");
        System.out.println("1 - Добавить в таблицы домашних животных");
        System.out.println("2 - Добавить в таблицы вьючных животных");
        System.out.println("3 - Добавить в таблицы всех животных");
        System.out.println("0 - Предыдущее меню");
        int command = command(scanner);
        if (command == 1) {
            updatePets();
        } else if (command == 2) {
            updatePackAnimals();
        } else if (command == 3) {
            updatePets();
            updatePackAnimals();
        } else if (command == 0) {
            System.out.println("Выход в предыдущее меню.");
        } else {
            System.out.println("Некорректная команда, попробуйте ещё раз.");
        }
    }

    private void selectSpecies() {
        System.out.println(connectionService.getInfo("SELECT * FROM animal_species;", 3));
    }

    private void selectAnimal() {
        System.out.println("Введите Animal_ID для поиска животного: ");
        System.out.println(createBDService.selectAnimal(command(scanner)));
    }

    private void updateAnimal() {
        System.out.println("Введите Animal_ID для поиска животного: ");
        int animalId = command(scanner);
        String animal = createBDService.selectAnimal(animalId);
        System.out.println(animal);
        System.out.println("--------------------------------------------");
        System.out.println("Введите команду:");
        System.out.println("1 - Дополнить команды животного");
        System.out.println("2 - Обновить команды животного");
        System.out.println("0 - Предыдущее меню");
        int command = command(scanner);
        if (command == 1) {
            String oldCommands = animal.split(", ")[4];
            System.out.println("Введите новые команды для животного: ");
            createBDService.updateCommandsAnimal(animalId, oldCommands + ", " + scanner.next());
            System.out.println("---------->");
            System.out.println(createBDService.selectAnimal(animalId));
        } else if (command == 2) {
            System.out.println("Введите новые команды для животного: ");
            createBDService.updateCommandsAnimal(animalId, scanner.next());
            System.out.println("---------->");
            System.out.println(createBDService.selectAnimal(animalId));
        } else if (command == 0) {
            System.out.println("Выход в предыдущее меню.");
        } else {
            System.out.println("Некорректная команда, попробуйте ещё раз.");
        }
    }

    private void clearTable() {
        System.out.println("Введите названия вида: ");
        createBDService.deleteTable(String.valueOf(new Scanner(System.in, "utf-8").next()));
    }

    private void getAnimalCount() {
        System.out.println("Общее число зарегистрированных животных равно: " + animalService.getCount());
    }

    private void selectTable() {
        System.out.println("Введите названия вида: ");
        System.out.println(createBDService.selectTable(String.valueOf(new Scanner(System.in, "utf-8").next())));
    }

    private void chooseAnimal() {
        System.out.println("Введите Animal_ID для поиска животного: ");
        System.out.println(createBDService.chooseAnimal(command(scanner)));
    }

    private void replacePets() {
        createBDService.replacePetsToDate();
    }
}
