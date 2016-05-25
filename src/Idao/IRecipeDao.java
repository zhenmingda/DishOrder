package Idao;

import dto.RecipeTo;

import java.util.List;


/**
 * Created by dashu on 2016/4/2.
 * version 1.0
 * The interface provides a read method fucntions
 */
public interface IRecipeDao {
     List<RecipeTo> readRecipe(String category);

}
