package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.Player;

public interface PlayerRepository extends 
        ReactiveMongoRepository<Player,Long> {
    //Mono<Player> findById(String name);
}
