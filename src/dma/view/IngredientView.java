package dma.view;

import dma.controller.MenuManagement;
import dma.database.ingredient.Ingredient;

import java.util.Scanner;

public class IngredientView {

    public void insertNewIngredient(int mealId){
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        System.out.println("Wieviele Zutaten werden hierfür benötigt?");
        int ingredients = scannerInt.nextInt();

        for (int j = 1; j <= ingredients; j++) {
            System.out.println("Geben sie Zutat " + j + " ein:");
            String nextIngredientName = scanner.nextLine();
            MenuManagement.insertIngredientIntoDB(mealId, new Ingredient(nextIngredientName));
        }
    }
}
