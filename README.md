[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/INcAwgxk)

# Portfolio project IDATT1003

This file uses Mark Down syntax. For more information see [here](https://www.markdownguide.org/basic-syntax/).

[//]: # "TODO: Fill inn your name and student ID"

STUDENT NAME = Dennis Moe  
STUDENT ID = 123631

## Project description

[//]: # "TODO: Write a short description of your project/product here."

   It's purpose is to act as a tracker-system for what you have in your fridge/kitchen
   along with the prices, measuring units, quantity and category of those items.

   The structure of the application is divided into 4 main parts:

   1. Fridge menu: Where you can add, remove and view the items in your fridge/kitchen.
      - I use the name "Fridge", however for ease of use there is no moral issue using it for storing
        items such as cooking oil or popcorn.

   3. The food fist menu: Where you can add, remove and view the ingredients registered.
      - This is basically a registry of previously bought items/the items in "the store".
        - If you want to add an item not on this list, you need to create it first.
           - This can be done in both the food list menu and in the fridge menu.


   4. Recipe menu: Where you can add, remove and view recipes.
      - There are several methods to make "your life easier".
         - You can get a list of recipes that you can make based on the contents
            of your fridge. Either fully or partially.
           - You can also check the price of a recipe.
                 - Both the full cost (regardless of how much you use) and the cost per serving.
              - You're also able to "use" the recipe,
                which will remove the ingredients from your fridge.
                - This is done by removing the amount of each ingredient used in the recipe.
                - Uses the items closest to expiration first.

   5. CookBook menu: Where you can add, remove and view cookbooks.
     - A cookbook is a collection of recipes.
     - You can manage cookbooks and add/remove recipes
       to a cookbook and view the recipes in a cookbook.

## Project structure
  
[//]: # "TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc."

The project structure is clearly defined. Everything referred to as the root directory is located within the fridgeApp/ folder of this project.

Within the project folder the project structure is based on the "Maven Standard Directory Layout", however I have chosen to exclude the com/... folder etc. because of the relatively simple project complexity.

My packages are located in the java directory: src/main/java/
 - models, services, utilities and the client folders are in here with their source 
   files.

The CSV files for exporting and importing user changes (such as the creation of ingredients or recipes) is saved within src/main/resources/data.
  - I.e. the foodlist.csv or the recipes.csv
   
Any compiled classes are located within the identically named folders of their non-compiled counterparts inside of: 
    out/production/fridgeApp/

The same goes for the JUnit-test classes which are located inside src/test folder of the main directory.

Any libraries related to testing, such as the hamcrest-core and the junit java-files are located inside the libs folder in the root directory.

Any presence of folders such as .idea or .vscode are accidental, but should have been removed by the project's completion.

## Link to repository

[//]: # "TODO: Include a link to your GitHub repository here."

The project should now be public and easily accessed through this link:
 - https://github.com/NTNU-IDI/idatt1003-mappe-2024-dennismoe0

## How to run the project

[//]: # "TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?"

To be added

## How to run the tests

[//]: # "TODO: Describe how to run the tests here."

To be added

## References

[//]: # "TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source."

To be added

hashmaps
https://www.youtube.com/watch?v=H62Jfv1DJlU&t=498s

w3schools hashmaps
https://www.w3schools.com/java/java_hashmap.asp

w3schools dates
https://www.w3schools.com/java/java_date.asp

unit testing
https://www.youtube.com/watch?v=vZm0lHciFsQ
