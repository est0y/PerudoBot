package ru.est0y.perudo.services;

import ru.est0y.perudo.domain.Game;

import java.util.List;
import java.util.Optional;

public interface GameService {
    Game save(Game game);
    void delete(Game game);

    List<Game> getGamesByPlayer(long playerId);
    Optional<Game> getGameByPlayer(long playerId);
    Optional<Game> findByTurnHolder(long playerId);
}
