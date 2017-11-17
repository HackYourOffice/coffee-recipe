package org.synyx.coffeecontrol.make.persistence;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
