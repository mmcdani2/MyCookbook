# MyCookbook

This simple application allows you to create, save, and update all of your favorite recipes. 

## Installing
1. Clone the repository to your local machine.
2. Drag the MyCookbook parent folder into the Android Studio welcome screen. 
3. Open the build.gradle app folder and ensure all dependencies are matching and up-to-date.
4. Check that project is using Gradle JDK 11 and source compatibility is set to Java 8.
5. Build the project and run on your desired device or emulator. 


## File Index
* **adapter:** package containing classes to control lists in app.
    * RecyclerViewAdapter: controls the list of recipes on the resipe screen.
* **data:** Package containing the classes defining the app's local database.
    * RecipeDao: Contains insertion, deletion, and query methods related to the Recipe table.
    * RecipeRepository: The object used to access the database. 
* **model:**
    * Recipe: Class that defines Recipe object. 
    * RecipeViewModel: Class that  uses LiveData to present the Recipe data to the UI. 
* **util:** 
    * RecipeRoomDatabase: Defines the recipe database.
* MainActivity: The main screen of the app, containing the controls for selecting and managing the current recipe or adding a new one.
* NewRecipe: The screen on the app that allows user to create update or delete recipes.

## Dependencies
androidx.appcompat:appcompat:1.2.0	implementation
androidx.appcompat:appcompat:1.5.1	implementation
androidx.constraintlayout:constraintlayout:2.1.4	implementation
androidx.test.espresso:espresso-core:3.5.0	androidTestImplementation
androidx.test.ext:junit:1.1.4	androidTestImplementation
junit:junit:4.13.2	testImplementation
androidx.lifecycle:lifecycle-common-java8:2.5.1	implementation
androidx.lifecycle:lifecycle-livedata:2.5.1	implementation
androidx.lifecycle:lifecycle-viewmodel:2.5.1	implementation
com.google.android.material:material:1.7.0	implementation
androidx.room:room-compiler:2.4.3	annotationProcessor
androidx.room:room-runtime:2.4.3	implementation
androidx.room:room-testing:2.4.3	androidTestImplementation
