# My Personal Project

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