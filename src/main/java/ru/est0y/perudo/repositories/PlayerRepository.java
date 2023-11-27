package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.est0y.perudo.domain.Player;

public interface PlayerRepository extends
        MongoRepository<Player,Long>
{
    //Mono<Player> findById(String name);
}
