package model.pack_animal;

import model.Animal;

import java.time.LocalDate;
import java.util.List;

public class PackAnimal extends Animal {
    public PackAnimal(int animalId, int specieId, String name, List<String> commands, LocalDate birthday) {
        super(animalId, specieId, name, commands, birthday);
    }
}
