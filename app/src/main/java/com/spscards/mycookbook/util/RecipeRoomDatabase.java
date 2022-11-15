package com.spscards.mycookbook.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.spscards.mycookbook.data.RecipeDao;
import com.spscards.mycookbook.model.Recipe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class RecipeRoomDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();
    public static final int NUMBER_OF_THREADS = 4;

    private static volatile RecipeRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RecipeRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (RecipeRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RecipeRoomDatabase.class, "recipe_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);

                    databaseWriteExecutor.execute(()->{
                        RecipeDao recipeDao = INSTANCE.recipeDao();
                        recipeDao.deleteAll();

//                        Recipe recipe = new Recipe("Brownie","Dessert","Ingredients", "Instructions");
//                        recipeDao.insert(recipe);
//
//                        recipe = new Recipe("Fudge", "Dessert", "Ingredients", "Instructions");
//                        recipeDao.insert(recipe);
//
//                        recipe = new Recipe("Cookie", "Dessert", "Ingredients", "Instructions");
//                        recipeDao.insert(recipe);



                    });
                }
            };
}
