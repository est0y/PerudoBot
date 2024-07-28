package ru.est0y.perudo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {

    List<Game> findByPlayersContaining(Player player);

    Game findOneByPlayersContaining(Player player);

    Game findOneByTurnHolder(Player player);

    Optional<Game> findOneByTurnHolderId(long playerId);

    List<Game>findByPlayersId(long playerId);

    Optional<Game> findOneByPlayersId(long playerId);
}
