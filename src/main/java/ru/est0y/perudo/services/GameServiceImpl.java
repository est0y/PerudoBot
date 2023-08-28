package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.repositories.GameRepository;
import ru.est0y.perudo.repositories.PlayerRepository;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Override
    public Mono<Game> save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Mono<Void> delete(Game game) {
        return gameRepository.delete(game);
    }

    @Override
    public Flux<Game> getGamesByPlayer(long playerId) {
        return gameRepository.findByPlayersId(playerId);
        /*return playerRepository.findById(playerId)
                .flatMapMany(gameRepository::findByPlayersContaining);*/
    }

    @Override
    public Mono<Game> getGameByPlayer(long playerId) {
        return gameRepository.findOneByPlayersId(playerId);
       // return playerRepository.findById(playerId).flatMap(gameRepository::findOneByPlayersContaining);
    }

    @Override
    public Mono<Game> findByTurnHolder(long playerId) {
        return playerRepository.findById(playerId).flatMap(gameRepository::findOneByTurnHolder);
    }
}
