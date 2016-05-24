package Idao;

import dto.RecipeTo;

import java.util.List;


/**
 * Created by dashu on 2016/4/2.
 * version 1.0
 * The interface provides two read methods fucntions
 */
public interface IRecipeDao {
     List<RecipeTo> readRecipeByCategory(String category);
     RecipeTo readRecipeByName(String name);

}
