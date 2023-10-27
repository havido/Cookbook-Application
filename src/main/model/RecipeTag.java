package model;

/*
 *  Represents recipes' tags to see whether this recipe can be modified:
 *  DEFAULT: from default library, view-only, can't be modified
 *  USER: added by user, can be modified
 *  DRAFT: haven't been added to library, still in construction
 */
public enum RecipeTag {
    DEFAULT, DRAFT;
    // Note: should have another tag USER, but can't implement it yet
}
