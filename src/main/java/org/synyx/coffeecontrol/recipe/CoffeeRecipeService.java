package org.synyx.coffeecontrol.recipe;

import org.springframework.stereotype.Component;

import org.synyx.coffeecontrol.Loggable;
import org.synyx.coffeecontrol.make.persistence.CoffeeRecipe;
import org.synyx.coffeecontrol.make.persistence.CoffeeRecipeRepository;
import org.synyx.coffeecontrol.make.persistence.Token;
import org.synyx.coffeecontrol.make.persistence.TokenRepository;
import org.synyx.coffeecontrol.recipe.web.CoffeeRecipeDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


@Component
public class CoffeeRecipeService implements Loggable {

    private final TokenRepository tokenRepository;
    private final CoffeeRecipeRepository coffeeRecipeRepository;

    public CoffeeRecipeService(TokenRepository tokenRepository, CoffeeRecipeRepository coffeeRecipeRepository) {

        this.tokenRepository = tokenRepository;
        this.coffeeRecipeRepository = coffeeRecipeRepository;
    }

    public Optional<CoffeeRecipe> getRecipeForToken(String token) {

        Optional<Token> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            logger().debug("found token {}", token);

            return Optional.of(optionalToken.get().getCoffeeRecipe());
        } else {
            return Optional.empty();
        }
    }


    public List<CoffeeRecipe> getAllCoffeeRecipes() {

        List<CoffeeRecipe> coffeeRecipes = new ArrayList<>();
        coffeeRecipeRepository.findAll().forEach(coffeeRecipes::add);

        return coffeeRecipes;
    }


    public void saveRecipe(CoffeeRecipe coffeeRecipe) {

        if (coffeeRecipe != null) {
            coffeeRecipeRepository.save(coffeeRecipe);
        } else {
            throw new IllegalArgumentException("will not save null");
        }
    }


    public static CoffeeRecipe toDto(CoffeeRecipeDto coffeeRecipeDto) {

        final CoffeeRecipe coffeeRecipe = new CoffeeRecipe(coffeeRecipeDto.command, coffeeRecipeDto.name);

        return coffeeRecipe;
    }


    @Transactional
    public void resetRecipes() {

        coffeeRecipeRepository.deleteAll();
        logger().info("deleted all recipe entries.");
    }


    @Transactional
    public void initRecipes() {

        coffeeRecipeRepository.save(new CoffeeRecipe("FA:03", "Einfacher Espresso"));
        coffeeRecipeRepository.save(new CoffeeRecipe("FA:04", "Doppelter Espresso"));
        coffeeRecipeRepository.save(new CoffeeRecipe("FA:05", "Einfacher Kaffee"));
        coffeeRecipeRepository.save(new CoffeeRecipe("FA:06", "Doppelter Kaffee"));
        logger().info("created {} recipe entries", coffeeRecipeRepository.count());
    }
}
