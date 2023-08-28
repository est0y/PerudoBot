package ru.est0y.perudo.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

public interface GameService {
    Mono<Game>save(Game game);
    Mono<Void>delete(Game game);

    Flux<Game> getGamesByPlayer(long playerId);
    Mono<Game> getGameByPlayer(long playerId);
    Mono<Game> findByTurnHolder(long playerId);
}
