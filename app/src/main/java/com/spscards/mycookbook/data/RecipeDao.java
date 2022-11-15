package com.spscards.mycookbook.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.spscards.mycookbook.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    //CRUD operations
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    @Query("SELECT * FROM recipe_table ORDER BY name ASC")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipe_table WHERE recipe_table.id == :id")
    LiveData<Recipe> get(int id);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

}
