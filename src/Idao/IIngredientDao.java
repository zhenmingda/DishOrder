package Idao;


import dto.IngredientTo;

import java.util.List;

/**
 * Created by dashu on 2016/4/17.
 * version 1.0
 * The interface provides a method of reading ingredients
 */
public interface IIngredientDao {
     List<IngredientTo> readIngredient();

}
