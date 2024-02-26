package model.pet;

import model.Animal;

import java.time.LocalDate;
import java.util.List;

public class Pet extends Animal {
    public Pet(int animalId, int specieId, String name, List<String> commands, LocalDate birthday) {
        super(animalId, specieId, name, commands, birthday);
    }
}
