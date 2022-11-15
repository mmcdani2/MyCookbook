package com.spscards.mycookbook.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.spscards.mycookbook.data.RecipeRepository;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    public static RecipeRepository repository;
    public final LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(@NonNull Application application){
        super(application);

        repository = new RecipeRepository(application);
        allRecipes = repository.getAllData();
    }

    public LiveData<List<Recipe>> getAllRecipes() { return allRecipes; }
    public static void insert(Recipe recipe) { repository.insert(recipe); }

    public LiveData<Recipe> get(int id){ return repository.get(id);}

    public void update(Recipe recipe){repository.update(recipe);}

    public void delete(Recipe recipe){repository.delete(recipe);}
}
