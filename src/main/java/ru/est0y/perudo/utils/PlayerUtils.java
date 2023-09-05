package ru.est0y.perudo.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerUtils {
    private final DiceUtils diceUtils;

    public Player getNextPlayer(Game game) {
        var playerNumber = game.getTurnHolder().getNumber();
        var players = game.getPlayers();
        if (playerNumber == players.size()) return players.get(0);
        return game.getPlayers().stream().filter(p -> p.isPlaying() && p.getNumber() > playerNumber).min(Comparator.comparing(Player::getNumber)).orElseThrow();

        //var playerNumber = game.getTurnHolder().getNumber();
        // var players = game.getPlayers();
        // return playerNumber == players.size() ? players.get(0) : players.get(playerNumber);
    }

    public Player getPreviousPlayer(Game game) {
        var playerNumber = game.getTurnHolder().getNumber();
        var players = game.getPlayers();
        if (playerNumber == 1) return players.get(players.size() - 1);
        return game.getPlayers().stream().filter(p -> p.isPlaying() && p.getNumber() < playerNumber).max(Comparator.comparing(Player::getNumber)).orElseThrow();
        // var playerNumber = game.getTurnHolder().getNumber();
        //var players = game.getPlayers();
        //return playerNumber == 1 ? players.get(players.size() - 1) : players.get(playerNumber - 2);
    }

    public void rollDice(Player player, int diceCount) {
        var dice = diceUtils.rollDice(diceCount);
        player.setDice(dice);
    }
}
