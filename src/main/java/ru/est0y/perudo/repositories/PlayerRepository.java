package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.est0y.perudo.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, Long> {

}
