package org.synyx.coffeecontrol.make.web;

import liquibase.util.StringUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.synyx.coffeecontrol.make.persistence.CoffeeRecipe;
import org.synyx.coffeecontrol.make.web.mqtt.CommandSender;
import org.synyx.coffeecontrol.recipe.CoffeeRecipeService;

import java.util.Optional;


@RestController
public class MakeController {

    private final CommandSender commandSender;
    private final CoffeeRecipeService coffeeRecipeService;

    public MakeController(CommandSender commandSender, CoffeeRecipeService coffeeRecipeService) {

        this.commandSender = commandSender;
        this.coffeeRecipeService = coffeeRecipeService;
    }

    @PostMapping("/make")
    public void makeForToken(@RequestBody TokenDto tokenDto) {

        if (StringUtils.isEmpty(tokenDto.token)) {
            System.err.println("received empty token");
        }

        Optional<CoffeeRecipe> coffeeRecipeOptional = coffeeRecipeService.getRecipeForToken(tokenDto.token);

        if (coffeeRecipeOptional.isPresent()) {
            commandSender.sendCommand(coffeeRecipeOptional.get().getCommand(), tokenDto.machineId);
        } else {
            System.out.println(String.format("will send command %s", coffeeRecipeOptional.get().getCommand()));
        }
    }
}
