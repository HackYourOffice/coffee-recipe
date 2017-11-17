package org.synyx.coffeecontrol.make;

import org.springframework.stereotype.Component;

import org.synyx.coffeecontrol.make.persistence.CoffeeRecipe;
import org.synyx.coffeecontrol.make.persistence.CoffeeRecipeRepository;
import org.synyx.coffeecontrol.make.persistence.Token;
import org.synyx.coffeecontrol.make.persistence.TokenRepository;
import org.synyx.coffeecontrol.make.web.TokenRecipeDto;


@Component
public class TokenService {

    private final TokenRepository tokenRepository;
    private final CoffeeRecipeRepository coffeeRecipeRepository;

    public TokenService(TokenRepository tokenRepository, CoffeeRecipeRepository coffeeRecipeRepository) {

        this.tokenRepository = tokenRepository;
        this.coffeeRecipeRepository = coffeeRecipeRepository;
    }

    public void saveToken(Token token) {

        tokenRepository.save(token);
    }


    public void saveFromTokenRecipeDto(final TokenRecipeDto tokenDto) {

        final Token token = new Token();

        // create coffee recipe from id
        final CoffeeRecipe coffeeRecipe = coffeeRecipeRepository.findOne(tokenDto.recipeId);
        token.setCoffeeRecipe(coffeeRecipe);
        token.setToken(tokenDto.token);
        tokenRepository.save(token);
    }
}
