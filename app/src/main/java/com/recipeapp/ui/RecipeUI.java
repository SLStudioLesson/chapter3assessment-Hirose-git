package com.recipeapp.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.recipeapp.datahandler.DataHandler;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class RecipeUI {
    private BufferedReader reader;
    private DataHandler dataHandler;

    public RecipeUI(DataHandler dataHandler) {
        reader = new BufferedReader(new InputStreamReader(System.in));
        this.dataHandler = dataHandler;
    }

    public void displayMenu() {

        System.out.println("Current mode: " + dataHandler.getMode());

        while (true) {
            try {
                System.out.println();
                System.out.println("Main Menu:");
                System.out.println("1: Display Recipes");
                System.out.println("2: Add New Recipe");
                System.out.println("3: Search Recipe");
                System.out.println("4: Exit Application");
                System.out.print("Please choose an option: ");

                String choice = reader.readLine();

                switch (choice) {
                    case "1":
                        displayRecipes();
                        break;
                    case "2":
                        addNewRecipe();
                        break;
                    case "3":
                        searchRecipe();
                        break;
                    case "4":
                        System.out.println("Exiting the application.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Error reading input from user: " + e.getMessage());
            }
        }
    }

    private void displayRecipes() {
        // レシピを読み込むメソッドを呼びリストに格納

        try {
            ArrayList<Recipe> recipes = dataHandler.readData();

            if (recipes.size() != 0) {

                System.out.println();
                System.out.println("Recipes:");
                System.out.println("-----------------------------------");

                // recipes の要素を分解して出力する
                for (Recipe recipe : recipes) {
                    // レシピ名の出力
                    System.out.println("Recipe Name: " + recipe.getName());
                    System.out.print("Main Ingredients: ");
                    // 材料名の出力
                    for (Ingredient ingredient : recipe.getIngredients()) {
                        System.out.print(ingredient.getName());
                        // 材料が最後の出力の最後の場合、 , を出力しない
                        if (!(recipe.getIngredients().get(recipe.getIngredients().size() - 1).getName()
                                .equals(ingredient.getName()))) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                    System.out.println("-----------------------------------");
                }

            } else { // レシピデータが空だった場合、表示
                System.out.println("No recipes available.");
            }

        } catch (IOException e) {
            System.out.println();
            System.out.println("Error reading file: " + e.getMessage());
        }

    }

    private void addNewRecipe() {

        try {

            System.out.println();
            System.out.println("Adding a new recipe.");
            System.out.print("Enter recipe name: ");
            String recipe = reader.readLine();

            System.out.println("Enter ingredients (type 'done' when finished):");

            ArrayList<Ingredient> ingredients = new ArrayList<>();

            // done が入力されるまで入力待ち受けする
            while (true) {
                System.out.print("Ingredient: ");
                String readIngredient = reader.readLine();

                // done が入力されたら繰り返しを抜ける
                if (readIngredient.equals("done")) {
                    break;
                }

                // 入力された材料名から Ingredient クラスを作成
                Ingredient ingredient = new Ingredient(readIngredient);

                // リストに ingredient クラスを格納
                ingredients.add(ingredient);

            }

            // Recipe クラスを引数として writeData を呼ぶ
            dataHandler.writeData(new Recipe(recipe, ingredients));

        } catch (Exception e) {
            System.out.println("Failed to add new recipe: " + e.getMessage());
        }
        // 処理が完了
        System.out.println("Recipe added successfully.");
    }

    private void searchRecipe() {


        try {

            System.out.print("Enter search query (e.g., 'name=Tomato&ingredient=Garlic'): ");
            String inputSearchWords = reader.readLine();

            dataHandler.searchData(inputSearchWords);
            
        } catch (IOException e) {
            System.out.println("Failed to search recipes: " + e.getMessage());
        }


    }
}
