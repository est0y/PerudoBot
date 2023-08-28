package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Flux<Game> findByPlayersContaining(Player player);
    Mono<Game> findOneByPlayersContaining(Player player);
    Mono<Game> findOneByTurnHolder(Player player);
    Flux<Game>findByPlayersId(long playerId);
    Mono<Game>findOneByPlayersId(long playerId);
}
