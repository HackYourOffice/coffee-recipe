package org.synyx.coffeecontrol.make.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Token {

    @Id
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "coffee_recipe_id")
    private CoffeeRecipe coffeeRecipe;

    public Long getId() {

        return id;
    }


    public void setId(Long id) {

        this.id = id;
    }


    public String getToken() {

        return token;
    }


    public void setToken(String token) {

        this.token = token;
    }


    public CoffeeRecipe getCoffeeRecipe() {

        return coffeeRecipe;
    }


    public void setCoffeeRecipe(CoffeeRecipe coffeeRecipe) {

        this.coffeeRecipe = coffeeRecipe;
    }
}
