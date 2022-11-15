package com.spscards.mycookbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spscards.mycookbook.adapter.RecyclerViewAdapter;
import com.spscards.mycookbook.model.Recipe;
import com.spscards.mycookbook.model.RecipeViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnRecipeClickListener{

    private static final int NEW_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Clicked";
    public static final String RECIPE_ID = "recipe_id";
    private RecipeViewModel recipeViewModel;
    private TextView textView;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LiveData<List<Recipe>> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(RecipeViewModel.class);

        recipeViewModel.getAllRecipes().observe(this, recipes -> {
            //set up adapter
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewAdapter = new RecyclerViewAdapter(recipes, MainActivity.this, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        FloatingActionButton fab = findViewById(R.id.add_recipe_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewRecipe.class);
            startActivityForResult(intent, NEW_RECIPE_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            assert data != null;
            String name = data.getStringExtra(NewRecipe.RECIPE_NAME_REPLY);
            String type = data.getStringExtra(NewRecipe.RECIPE_TYPE_REPLY);
            String ingredient = data.getStringExtra(NewRecipe.INGREDIENTS_REPLY);
            String instructions = data.getStringExtra(NewRecipe.INSTRUCTIONS_REPLY);

            assert name != null;
            Recipe recipe = new Recipe(name, type, ingredient, instructions);

            RecipeViewModel.insert(recipe);
        }
    }

    @Override
    public void onRecipeClick(int position) {
        Recipe recipe = Objects.requireNonNull(recipeViewModel.allRecipes.getValue()).get(position);

        Intent intent = new Intent(MainActivity.this, NewRecipe.class);
        intent.putExtra(RECIPE_ID, recipe.getId());
        startActivity(intent);

    }
}