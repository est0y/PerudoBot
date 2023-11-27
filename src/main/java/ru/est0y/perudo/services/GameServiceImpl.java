package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.repositories.GameRepository;
import ru.est0y.perudo.repositories.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public List<Game> getGamesByPlayer(long playerId) {
        return gameRepository.findByPlayersId(playerId);
        /*return playerRepository.findById(playerId)
                .flatMapMany(gameRepository::findByPlayersContaining);*/
    }

    @Override
    public Optional<Game> getGameByPlayer(long playerId) {
        return gameRepository.findOneByPlayersId(playerId);
        // return playerRepository.findById(playerId).flatMap(gameRepository::findOneByPlayersContaining);
    }

    @Override
    public Optional<Game> findByTurnHolder(long playerId) {
        return gameRepository.findOneByTurnHolderId(playerId);
        //return playerRepository.findById(playerId).flatMap(gameRepository::findOneByTurnHolder);
    }
}
