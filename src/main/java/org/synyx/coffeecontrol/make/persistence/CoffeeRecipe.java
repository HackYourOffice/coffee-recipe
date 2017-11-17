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

    private String name;

    // for spring data jpa
    public CoffeeRecipe() {
    }


    public CoffeeRecipe(String command, String name) {

        this.command = command;
        this.name = name;
    }

    public String getName() {

        return name;
    }


    public void setName(String name) {

        this.name = name;
    }


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
