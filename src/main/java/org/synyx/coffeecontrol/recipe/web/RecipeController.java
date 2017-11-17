package org.synyx.coffeecontrol.recipe.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.synyx.coffeecontrol.make.CoffeeRecipeService;


@RestController
public class RecipeController {

    private final CoffeeRecipeService coffeeRecipeService;

    public RecipeController(CoffeeRecipeService coffeeRecipeService) {

        this.coffeeRecipeService = coffeeRecipeService;
    }

    @PostMapping("/recipe")
    public void addRecipe(@RequestBody final CoffeeRecipeDto coffeeRecipeDto) {

        if (coffeeRecipeDto != null) {
            coffeeRecipeService.saveRecipe(CoffeeRecipeService.toDto(coffeeRecipeDto));
        }
    }


    @GetMapping("/recipe/reset")
    public void resetRecipes() {

        coffeeRecipeService.resetRecipes();
    }


    @GetMapping("/recipe/init")
    public void initRecipes() {

        coffeeRecipeService.initRecipes();
    }
}
