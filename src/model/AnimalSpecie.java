package model;

public class AnimalSpecie {
    private int animalSpeciesId;
    private Specie name;
    private int parentId;

    public AnimalSpecie(int animalSpeciesId, Specie name, int parentId) {
        this.animalSpeciesId = animalSpeciesId;
        this.name = name;
        this.parentId = parentId;
    }

    public int getAnimalSpeciesId() {
        return animalSpeciesId;
    }

    public Specie getName() {
        return name;
    }

    public int getParentId() {
        return parentId;
    }
}
