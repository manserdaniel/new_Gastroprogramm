package dma.database.order;

import dma.database.ingredient.Ingredient;
import dma.database.meal.Meal;

import java.util.List;

public class OrderPart {
    private Meal meal;
    private double price;
    private int countAddedIngredients = 0;

    List<Ingredient> ingredients;

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
        this.price = meal.getPrice();
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void setIngredient(Ingredient ingredients) {
        this.ingredients.add(ingredients);
    }

    public int getCountAddedIngredients() {
        return countAddedIngredients;
    }

    public void setCountAddedIngredients(int countAddedIngredients) {
        this.countAddedIngredients = countAddedIngredients;
    }

    public void deleteIngredient(int ingredientId) {
        this.ingredients.remove(ingredientId);
    }

    public void addIngredient(String name) {
        ingredients.add(new Ingredient(name));
        this.price = this.price + 0.5;
        this.countAddedIngredients++;
    }

}
