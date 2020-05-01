package dma.database.meal;

import dma.database.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Meal {

    private int id = 0;
    private String name = "";
    private double price = 0;
    private List<Ingredient> ingredients= new ArrayList<>();

    public Meal(String name, String price) {
        this.name = name;
        this.price = Double.parseDouble(price);
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

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void getIngredient() {

    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }
}
