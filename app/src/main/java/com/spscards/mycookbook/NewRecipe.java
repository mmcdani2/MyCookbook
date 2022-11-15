package com.spscards.mycookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.spscards.mycookbook.model.Recipe;
import com.spscards.mycookbook.model.RecipeViewModel;

public class NewRecipe extends AppCompatActivity {

    public static final String RECIPE_NAME_REPLY = "recipe_name_reply";
    public static final String RECIPE_TYPE_REPLY = "recipe_type_reply";
    public static final String INGREDIENTS_REPLY = "ingredients_reply";
    public static final String INSTRUCTIONS_REPLY = "instructions_reply";
    private EditText enterRecipe;
    private EditText enterType;
    private EditText enterIngredients;
    private EditText enterInstructions;
    private Button saveButton;
    private int recipeId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        enterRecipe = findViewById(R.id.enter_recipe_name);
        enterType = findViewById(R.id.enter_recipe_type);
        enterIngredients = findViewById(R.id.enter_ingredients);
        enterInstructions = findViewById(R.id.enter_instructions);
        saveButton = findViewById(R.id.save_button);

        recipeViewModel = new ViewModelProvider.AndroidViewModelFactory(NewRecipe.this
                .getApplication())
                .create(RecipeViewModel.class);

        if(getIntent().hasExtra(MainActivity.RECIPE_ID)){
            recipeId = getIntent().getIntExtra(MainActivity.RECIPE_ID, 0);
            recipeViewModel.get(recipeId).observe(this, recipe -> {
                if(recipe != null){
                    enterRecipe.setText(recipe.getName());
                    enterType.setText(recipe.getType());
                    enterIngredients.setText(recipe.getIngredients());
                    enterInstructions.setText(recipe.getInstructions());
                }
            });
            isEdit = true;
        }

        saveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if(!TextUtils.isEmpty(enterRecipe.getText()) && !TextUtils.isEmpty(enterType.getText())){
                String recipeName = enterRecipe.getText().toString();
                String recipeType = enterType.getText().toString();
                String ingredients = enterIngredients.getText().toString();
                String instructions = enterInstructions.getText().toString();

                replyIntent.putExtra(RECIPE_NAME_REPLY, recipeName);
                replyIntent.putExtra(RECIPE_TYPE_REPLY, recipeType);
                replyIntent.putExtra(INGREDIENTS_REPLY, ingredients);
                replyIntent.putExtra(INSTRUCTIONS_REPLY, instructions);
                setResult(RESULT_OK, replyIntent);
            }else{
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();
        });

        //delete button
        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> editRecipe(true));

        //update button
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> editRecipe(false));

        if(isEdit){
            saveButton.setVisibility(View.GONE);
        }else{
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

    }

    private void editRecipe(Boolean isDelete) {
        String name = enterRecipe.getText().toString().trim();
        String type = enterType.getText().toString().trim();
        String ingredients = enterIngredients.getText().toString().trim();
        String instructions = enterInstructions.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(type)){
            Snackbar.make(enterRecipe, R.string.empty, Snackbar.LENGTH_SHORT).show();
        }else{
            Recipe recipe = new Recipe();
            recipe.setId(recipeId);
            recipe.setName(name);
            recipe.setType(type);
            recipe.setIngredients(ingredients);
            recipe.setInstructions(instructions);
            if(isDelete){
                recipeViewModel.delete(recipe);
            }else{
                recipeViewModel.update(recipe);
            }

            finish();
        }
    }
}