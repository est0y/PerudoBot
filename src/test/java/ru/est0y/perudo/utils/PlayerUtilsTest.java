package ru.est0y.perudo.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PlayerUtils.class)
class PlayerUtilsTest {
    @MockBean
    private DiceUtils diceUtils;
    @Autowired
    private PlayerUtils playerUtils;

    @Test
    void getNextPlayer() {
        var players = createPlayers(true, true, false);
        var game = createGame(players.get(1), players);
        Assertions.assertEquals(1, playerUtils.getNextPlayer(game).getNumber());

        players = createPlayers(true, false, true);
        game = createGame(players.get(0), players);
        Assertions.assertEquals(3, playerUtils.getNextPlayer(game).getNumber());

        players = createPlayers(true, false, true);
        game = createGame(players.get(2), players);
        Assertions.assertEquals(1, playerUtils.getNextPlayer(game).getNumber());

        players = createPlayers(false, true, true);
        game = createGame(players.get(2), players);
        Assertions.assertEquals(2, playerUtils.getNextPlayer(game).getNumber());

        players = createPlayers(true, true, false);
        game = createGame(players.get(1), players);
        Assertions.assertEquals(1, playerUtils.getNextPlayer(game).getNumber());
        game = createGame(players.get(0), players);
        Assertions.assertEquals(2, playerUtils.getNextPlayer(game).getNumber());
    }

    @Test
    void getPreviousPlayer() {
        var players = createPlayers(true, true, false);
        var game = createGame(players.get(1), players);
        Assertions.assertEquals(1, playerUtils.getPreviousPlayer(game).getNumber());

        players = createPlayers(true, false, true);
        game = createGame(players.get(0), players);
        Assertions.assertEquals(3, playerUtils.getPreviousPlayer(game).getNumber());

        players = createPlayers(true, false, true);
        game = createGame(players.get(2), players);
        Assertions.assertEquals(1, playerUtils.getPreviousPlayer(game).getNumber());

        players = createPlayers(false, true, true);
        game = createGame(players.get(2), players);
        Assertions.assertEquals(2, playerUtils.getPreviousPlayer(game).getNumber());

        players = createPlayers(true, true, false);
        game = createGame(players.get(1), players);
        Assertions.assertEquals(1, playerUtils.getPreviousPlayer(game).getNumber());
        game = createGame(players.get(0), players);
        Assertions.assertEquals(2, playerUtils.getPreviousPlayer(game).getNumber());

        players = createPlayers(true, true, true);
        game = createGame(players.get(2), players);
        Assertions.assertEquals(2, playerUtils.getPreviousPlayer(game).getNumber());
    }

    private Game createGame(Player turnHolder, List<Player> players) {
        return new Game(null, 0L, turnHolder, players, null, 0, 0, false);
    }

    private List<Player> createPlayers(Boolean... booleans) {
        var atomicInt = new AtomicInteger(1);
        return Arrays.stream(booleans)
                .map(isPlaying -> new Player(0L, "any", atomicInt.getAndIncrement(), List.of(1, 2, 3), isPlaying))
                .toList();
    }

}