package dma.database.ingredient;


public class Ingredient {
    private int id = 0;
    private String name = "";
    private String counter = "";

    public Ingredient(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public boolean equals(Object o) {
        Ingredient ingredient = (Ingredient) o;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return name.equalsIgnoreCase(((Ingredient) o).name);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }
}


