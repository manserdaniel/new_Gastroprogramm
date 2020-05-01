package dma.view;

import dma.controller.MenuManagement;
import dma.database.MealIngredientsRepository;
import dma.database.ingredient.Ingredient;
import dma.database.meal.Meal;

import java.util.List;
import java.util.Scanner;

public class MealView {

    public void printTable(List<Meal> meals){
        System.out.println("ID - Name - Preis");

        for (Meal meal: meals) {

            System.out.println(meal.getId() + " - " + meal.getName() + " - " + meal.getPrice() + "€");
        }
    }

    public void printMeal(Meal meal){
        System.out.println("ID - Name - Preis");
        System.out.println(meal.getId() + " - " + meal.getName() + " - " + meal.getPrice() + "€");
    }

    public void printFullMenu(List<Meal> meals){
        System.out.println("ID - Name - Preis");
        for (Meal meal: meals) {
            System.out.println(meal.getId() + " - " + meal.getName() + " - " + meal.getPrice() + "€");
            MealIngredientsRepository mealIngredientsRepository = new MealIngredientsRepository();
            List<Ingredient> ingredientsList = null;
            ingredientsList = mealIngredientsRepository.findIngredientsOfMeal(meal.getId());

            for (Ingredient ingredient: ingredientsList) {

                System.out.println(ingredient.getName() + "  ");
            }
        }
    }


    public void insertNewMeals() {
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        System.out.println("Wie viele Speisen möchten Sie gerne eintragen?");
        int amountOfMeals = scannerInt.nextInt();

        for (int i = 1; i <= amountOfMeals; i++) {

            System.out.println("Geben sie eine neue Speise ein:");
            String nextMealName = scanner.nextLine();

            System.out.println("Geben sie den Preis dieser Speise ein:");
            String nextMealPrice = scanner.nextLine();

            int newMenuId = MenuManagement.insertMenuIntoDB(new Meal(nextMealName, nextMealPrice));

            IngredientView ingredientView = new IngredientView();
            ingredientView.insertNewIngredient(newMenuId);
        }
    }


}
