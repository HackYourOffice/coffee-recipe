package org.synyx.coffeecontrol.make.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class CoffeeRecipe {

    @Id
    @GeneratedValue
    private Long id;

    private String command;

    public Long getId() {

        return id;
    }


    public void setId(Long id) {

        this.id = id;
    }


    public String getCommand() {

        return command;
    }


    public void setCommand(String command) {

        this.command = command;
    }
}
