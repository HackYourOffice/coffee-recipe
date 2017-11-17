package org.synyx.coffeecontrol.make.persistence;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface CoffeeRecipeRepository extends CrudRepository<CoffeeRecipe, Long> {
}
