package org.synyx.coffeecontrol.make.web;

import liquibase.util.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.synyx.coffeecontrol.Loggable;
import org.synyx.coffeecontrol.make.persistence.CoffeeRecipe;
import org.synyx.coffeecontrol.make.web.mqtt.CommandSender;
import org.synyx.coffeecontrol.recipe.CoffeeRecipeService;

import java.util.Optional;


@RestController
public class MakeController implements Loggable {

    private final CommandSender commandSender;
    private final CoffeeRecipeService coffeeRecipeService;

    public MakeController(CommandSender commandSender, CoffeeRecipeService coffeeRecipeService) {

        this.commandSender = commandSender;
        this.coffeeRecipeService = coffeeRecipeService;
    }

    @PostMapping("/make")
    public void makeForToken(@RequestBody TokenDto tokenDto) {

        sendCommandIfTokenIsPresent(tokenDto);
    }


    private void sendCommandIfTokenIsPresent(@RequestBody TokenDto tokenDto) {

        if (StringUtils.isEmpty(tokenDto.token)) {
            logger().info("received an empty token command. will not look for recipes");
        }

        Optional<CoffeeRecipe> coffeeRecipeOptional = coffeeRecipeService.getRecipeForToken(tokenDto.token);

        if (coffeeRecipeOptional.isPresent()) {
            commandSender.sendCommand(coffeeRecipeOptional.get().getCommand(), tokenDto.machineId);
        } else {
            logger().info("did not find recipe for token {}", tokenDto.token);
        }
    }


    @GetMapping("/make")
    public void makeForTokenGet(@RequestParam(name = "token") final String token,
        @RequestParam(name = "machine") final String machineId) {

        final TokenDto tokenDto = new TokenDto();
        tokenDto.token = token;
        tokenDto.machineId = machineId;
        sendCommandIfTokenIsPresent(tokenDto);
    }
}
