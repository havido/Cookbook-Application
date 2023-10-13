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

- As a user, I want to be able to filter recipes based on names, ingredients,
dietary requirements, and/or time consumption
- As a user, I want to be able to view the list of recipes after filtering
- As a user, I want to be able to add my recipes to my collections
- As a user, I want to be able to improvise a recipe from the original database,
and keep a record of it
- As a user, I want to be able to select a recipe in the database and rate it, as
well as comment on it

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