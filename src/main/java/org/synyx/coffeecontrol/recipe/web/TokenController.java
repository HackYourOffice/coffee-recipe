package org.synyx.coffeecontrol.recipe.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.synyx.coffeecontrol.make.TokenService;
import org.synyx.coffeecontrol.make.web.TokenRecipeDto;


@RestController
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {

        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public void saveToken(@RequestBody final TokenRecipeDto tokenRecipeDto) {

        tokenService.saveFromTokenRecipeDto(tokenRecipeDto);
    }
}
