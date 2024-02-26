package model;

import java.time.LocalDate;
import java.util.List;

public class Animal {
    private int animalId;
    private int specieId;
    private String name;
    private List<String> commands;
    private LocalDate birthday;

    public Animal(int animalId, int specieId, String name, List<String> commands, LocalDate birthday) {
        this.animalId = animalId;
        this.specieId = specieId;
        this.name = name;
        this.commands = commands;
        this.birthday = birthday;
    }

    public Animal() {
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public void setSpecieId(int specieId) {
        this.specieId = specieId;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getAnimalId() {
        return animalId;
    }

    public int getSpecieId() {
        return specieId;
    }

    public String getName() {
        return name;
    }

    public List<String> getCommands() {
        return commands;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return ":Животное -----> ||" +
                "ID = " + animalId +
                ", видовой ID = " + specieId +
                ", имя = '" + name + '\'' +
                ", команды = " + commands +
                ", день рождения = " + birthday +
                "||";
    }
}
