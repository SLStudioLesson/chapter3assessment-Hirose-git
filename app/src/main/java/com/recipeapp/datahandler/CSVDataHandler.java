package com.recipeapp.datahandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;

public class CSVDataHandler implements DataHandler {
    private String filePath;

    public CSVDataHandler() {
        this.filePath = "app/src/main/resources/recipes.csv";
    }

    public CSVDataHandler(String filepath) {
        this.filePath = filepath;
    }

    // 文字列CSVを返してください。
    @Override
    public String getMode() {
        return "CSV";
    }

    // 以降の設問で処理を実装するため定義し、nullをreturnしてください。
    @Override
    public ArrayList<Recipe> readData() throws IOException {
        ArrayList<Recipe> recipes = new ArrayList<>();
        // recipes.csv を読み込む
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        // 値がnullになるまで読み込みを繰り返す
        while ((line = reader.readLine()) != null) {
            // 読み込んだ行を分割
            // row[0] はレシピ名
            // reo[1] 以降は材料、材料数はレシピにより異なる
            String[] row = line.split(",");
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            for (int i = 1; i < row.length; i++) {
                Ingredient ingredient = new Ingredient(row[i]);
                ingredients.add(ingredient);
            }

            recipes.add(new Recipe(row[0], ingredients));
        }
        reader.close();

        return recipes;
    }

    // 以降の設問で処理を実装するため定義のみ行います。
    @Override
    public void writeData(Recipe recipe) throws IOException {
        // 入力したレシピを受け取り、 recipes.txt に追記する
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
        // レシピ名を変数に格納
        String inputData = recipe.getName();
        // , と 材料名を変数にプラスする
        for (Ingredient ingredient : recipe.getIngredients()) {
            inputData += "," + ingredient.getName();
        }
        // 改行して recipes.csv に追記する
        writer.newLine();
        writer.write(inputData);
        writer.close();

    }

    // 以降の設問で処理を実装するため定義し、nullをreturnしてください。
    @Override
    public ArrayList<Recipe> searchData(String keyword) throws IOException {

        // 入力した検索文字列か正しい形式かを判定
        if (keyword.contains("&") && keyword.contains("=") && keyword.contains("name")
                && keyword.contains("ingredient")) {

                    

        } else { // 入力形式を満たしていなかった場合以下を表示する
            System.out.println("No recipes found matching the criteria.");
        }

        return null;
    }

}
