package org.synyx.coffeecontrol.make;

import org.springframework.stereotype.Component;

import org.synyx.coffeecontrol.make.persistence.CoffeeRecipe;
import org.synyx.coffeecontrol.make.persistence.Token;
import org.synyx.coffeecontrol.make.persistence.TokenRepository;

import java.util.Optional;


@Component
public class CoffeeRecipeService {

    private final TokenRepository tokenRepository;

    public CoffeeRecipeService(TokenRepository tokenRepository) {

        this.tokenRepository = tokenRepository;
    }

    public Optional<CoffeeRecipe> getRecipeForToken(String token) {

        Optional<Token> optionalToken = tokenRepository.findByToken(token);

        if (optionalToken.isPresent()) {
            return Optional.of(optionalToken.get().getCoffeeRecipe());
        } else {
            return Optional.empty();
        }
    }
}
