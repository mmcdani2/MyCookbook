package com.spscards.mycookbook.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.spscards.mycookbook.model.Recipe;
import com.spscards.mycookbook.util.RecipeRoomDatabase;

import java.util.List;

public class RecipeRepository {
    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> allRecipes;

    public RecipeRepository(Application application) {
        RecipeRoomDatabase db = RecipeRoomDatabase.getDatabase(application);
        recipeDao = db.recipeDao();

        allRecipes = recipeDao.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllData() { return allRecipes; }
    public void insert(Recipe recipe){
        RecipeRoomDatabase.databaseWriteExecutor.execute(() ->{
            recipeDao.insert(recipe);
        });
    }

    public LiveData<Recipe> get(int id){
        return recipeDao.get(id);
    }

    public void update(Recipe recipe){
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> recipeDao.update(recipe));
    }

    public void delete(Recipe recipe){
        RecipeRoomDatabase.databaseWriteExecutor.execute(() -> recipeDao.delete(recipe));
    }
}
