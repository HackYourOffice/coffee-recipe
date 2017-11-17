package org.synyx.coffeecontrol.make;

import org.springframework.stereotype.Component;

import org.synyx.coffeecontrol.Loggable;
import org.synyx.coffeecontrol.make.persistence.CoffeeRecipe;
import org.synyx.coffeecontrol.make.persistence.CoffeeRecipeRepository;
import org.synyx.coffeecontrol.make.persistence.Token;
import org.synyx.coffeecontrol.make.persistence.TokenRepository;
import org.synyx.coffeecontrol.make.web.TokenRecipeDto;


@Component
public class TokenService implements Loggable {

    private final TokenRepository tokenRepository;
    private final CoffeeRecipeRepository coffeeRecipeRepository;

    public TokenService(TokenRepository tokenRepository, CoffeeRecipeRepository coffeeRecipeRepository) {

        this.tokenRepository = tokenRepository;
        this.coffeeRecipeRepository = coffeeRecipeRepository;
    }

    public void saveFromTokenRecipeDto(final TokenRecipeDto tokenDto) {

        final Token token = new Token();

        // create coffee recipe from id
        final CoffeeRecipe coffeeRecipe = coffeeRecipeRepository.findOne(tokenDto.recipeId);

        // only store token when a recipe exists for this id
        if (coffeeRecipe != null) {
            token.setCoffeeRecipe(coffeeRecipe);
            token.setToken(tokenDto.token);
            tokenRepository.save(token);
            logger().info("successfully saved token {}", tokenDto);
        } else {
            logger().error("will not save token {} since no recipe was found for recipeId.", tokenDto);
        }
    }
}
