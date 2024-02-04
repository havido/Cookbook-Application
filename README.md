# My Personal Project

This is a course project for the course CPSC 210 at UBC. This project builds a local PC cookbook application that lets people search up recipes from local library, and add more recipes into that library.

[//]: # (## A subtitle)

[//]: # ()
[//]: # (A *bulleted* list:)

[//]: # (- item 1)

[//]: # (- item 2)

[//]: # (- item 3)

[//]: # ()
[//]: # (An example of text with **bold** and *italic* fonts.  )

## Idea generation

### Problems that I face in everyday life

- "What to have for dinner?"
- "Shit, some ingredients are starting to rot. I'm too poor to throw them away,
how should I cook them?" 
- "I bought this cucumber but it tastes bad so I don't feel like eating it 
raw, is there any recipe that use cooked cucumber?"

### Project idea to solve these problems

- A simple cookbook application that allows user to let them search for the
exact recipe that they want. However, it also allows user to filter recipes by
types of dish, ingredients, dietary requirements, and time consumption.

### Who will use it?

- Me & roommate
- Working people who live by themselves: only buy groceries occasionally but low
consumption -> usually see their ingredients go rotten
- People who crave for weird combinations of ingredients but don't know how to
process them, or don't want to visit the washroom 10 times after they
eat their cooking

### User stories

- [x] As a user, I want to be able to filter recipes based on names, ingredients,
dietary requirements, and/or time consumption
- [x] As a user, I want to be able to view the list of recipes after filtering
- [x] As a user, I want to be able to add my recipes to the library
- [x] As a user, I want to be able to load a library of my choice for the program to be run upon
- [x] As a user, I want to be able to save my recipe to the library (if I so choose)
- [x] As a user, when I start the application, I want to be given the option to load my 
half-constructed recipe from file.
- [x] As a user, I want to be able to view a recipe using its ID
- [ ] As a user, I want to be able to select a recipe in the database and rate it, as
well as comment on it

### Instructions for Grader

- Initially, you have to choose a library for the program to be run upon to proceed to
the next steps. Here, you should click on the "Default Library" button just because
this library has more recipes than the other one, thus makes it more useful to test on
functions. This satisfies the user story of loading the state of the application from file.
- For the user story "add multiple Xs to a Y", click "Add a new recipe"
- You now have the option to edit current drafts or create a new recipe. Click any.
- If you chose to edit a draft, some inputs have been read from the draft and initialised
for you. If you chose to create a new one, all inputs are blank.
- For Ingredients and Steps, when you want to add a new ingredient or step, click the "Add..."
button in their corresponding panel
- If you want to delete an existing ingredient or step, just leave the input blank
- Click "save to draft" or "save for real" to save to the .json library. This satisfies the
user story where user can choose to save their work to file.
- "Save to draft": save to the drafts library. Your draft will now be accessible from the
menu of drafts
- "Save for real": save to the default library. Your recipe must not have any blank inputs
in order to save successfully
- For the case that "all the Xs that have already been added to Y are displayed": click 
"Search a recipe" from the second panel at the start
- To view all recipes added in the default library, leave all search bars blank, and hit
"Search!"
- Otherwise, you can filter as you like, and hit "Search!"
- To view a specific recipe, click on the button. A frame will pop up that include the recipe's
information, and its image. Note that recipes added by user have yet to have the function
to include images. I didn't have enough time...

### Phase 4: Task 2

Fri Dec 01 16:14:13 PST 2023
Library loaded from: ./data/library.json


Fri Dec 01 16:14:13 PST 2023
Added recipe Ground Beef Spinach Casserole to library [ID: 1]


Fri Dec 01 16:14:13 PST 2023
Added recipe Mexican Ground Beef Casserole to library [ID: 2]


Fri Dec 01 16:14:13 PST 2023
Added recipe Spaghetti Pie to library [ID: 3]


Fri Dec 01 16:14:13 PST 2023
Added recipe Chef John's Buttermilk Fried Chicken to library [ID: 4]


Fri Dec 01 16:14:13 PST 2023
Added recipe Classic Meatloaf to library [ID: 5]


Fri Dec 01 16:14:13 PST 2023
Added recipe Greek Couscous Salad to library [ID: 6]


Fri Dec 01 16:14:13 PST 2023
Added recipe Vi's breakfast toast to library [ID: 7]


Fri Dec 01 16:14:13 PST 2023
Added recipe CatCatCat to drafts [ID: 8]


Fri Dec 01 16:14:13 PST 2023
Added recipe Oatmilk latte to library [ID: 9]


Fri Dec 01 16:14:13 PST 2023
Added recipe Scrambled eggs with tomato to library [ID: 10]


Fri Dec 01 16:14:13 PST 2023
Added recipe hehe to drafts [ID: 11]


Fri Dec 01 16:14:13 PST 2023
Added recipe Pork and Cabbage Dumpling to library [ID: 12]


Fri Dec 01 16:14:13 PST 2023
Current library record updated. Total number of works: 12, number of recipes in library: 10, number of drafts: 2


Fri Dec 01 16:14:23 PST 2023
Filtered result based on name: toast


Fri Dec 01 16:14:23 PST 2023
Filtered result based on time: 1000


Fri Dec 01 16:14:56 PST 2023
Added ingredient cabbage to recipe drafthehe


Fri Dec 01 16:14:56 PST 2023
Added ingredient cucumber to recipe drafthehe


Fri Dec 01 16:14:56 PST 2023
Added ingredient butter to recipe drafthehe


Fri Dec 01 16:14:56 PST 2023
Library saved to: ./data/library.json

### Phase 4: Task 3

#### Some ideas I would like to add

- When the user encounters the screen where they add/edit a recipe, they have the option
to upload a photo of their recipe to the program, which will be stored in ./images/
- I want to edit the application so that it looks better visually
- I want to make the program more real - perhaps I can make a tool that reads recipes
from a specific tool from the internet and store it in the .json file? But that would
take a long time

#### Refactoring

- I didn't really make custom exceptions, so if I had more time I could work on that
- Separated panels into even more classes for readability. Right now some
of my classes have 5~6 panels, maybe even more, which was extremely confusing for me
to code at times, and I doubt people would have a great time reading my codes
- An abstract class for my panels so that every panel is configured in the
same way
- For the GUI, the user can load a library and the program will depend on this library
throughout the program. I could try refactoring it into simpleton design pattern

### Scrap ideas

- Database of recipes will be stored in a .txt file. These recipes, when searched,
will be marked "from the default database"
- Users are allowed to add more recipes to the database, however these recipes will
be marked "your recipes" -> if they get food poisoning it's not the programmer's
nor the database's fault
- Ingredients that can be filtered will not include spices (salt, pepper, sugar etc.)
- Perhaps each ingredient can have a normal version and special versions for
people with dietary requirements? - might expand the database A LOT
- After the user tries out a recipe, they could mark it done, and give it a
rating/comment if they want. These ratings and comments will be kept in a different
database (?) For each time they mark it done, the application will also increment
number of trial by 1
- They could also keep a record of each time they improvise the recipe. If they love
the improvisation, they have the option to duplicate the original recipe with
their modification. When searched up, this recipe will be marked "improvised duplicate"
- If a user absolutely hates a certain recipe for some reason, they could "report"
it - in this case, delete it from the database (even the default database)
- If I want to make it more advanced, I could use another database that specifies
which ingredient can be substituted by which -> avoids the issue of not being able
to find any matched recipe. When recipes with substituted ingredients are searched
up, a match percentage will be shown alongside
