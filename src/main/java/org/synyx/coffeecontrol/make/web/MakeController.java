package org.synyx.coffeecontrol.make.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.synyx.coffeecontrol.make.web.mqtt.CommandSender;


@RestController("/make")
public class MakeController {

    private final CommandSender commandSender;

    public MakeController(CommandSender commandSender) {

        this.commandSender = commandSender;
    }

    @PostMapping
    public void makeCommand(@RequestBody String command) {

        commandSender.sendCommand("penis");
    }
}
