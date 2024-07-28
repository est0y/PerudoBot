package ru.est0y.perudo.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerUtils {
    private final DiceUtils diceUtils;

    public Player getNextPlayer(Game game) {
        log.info("turn holder name = {}", game.getTurnHolder().getName());
        var playerNumber = game.getTurnHolder().getNumber();
        var players = game.getPlayers().stream().filter(Player::isPlaying).sorted(Comparator.comparing(Player::getNumber)).toList();
        var endNumber = players.stream().max(Comparator.comparing(Player::getNumber)).orElseThrow().getNumber();
        if ((playerNumber == endNumber)) {
            return players.get(0);
        }
        int turnHolderIndex = players.indexOf(game.getTurnHolder());
        log.info("next player name = {}", players.get(turnHolderIndex + 1).getName());
        return players.get(turnHolderIndex + 1);
    }

    public Player getPreviousPlayer(Game game) {
        var playerNumber = game.getTurnHolder().getNumber();
        var players = game.getPlayers().stream().filter(Player::isPlaying).sorted(Comparator.comparing(Player::getNumber)).toList();
        int turnHolderIndex = players.indexOf(game.getTurnHolder());
        var firstNumber = players.stream().min(Comparator.comparing(Player::getNumber)).orElseThrow().getNumber();
        if (firstNumber == playerNumber) return players.get(players.size() - 1);
        log.info("previous player name = {}", players.get(turnHolderIndex - 1).getName());
        return players.get(turnHolderIndex - 1);
    }

    public void rollDice(Player player, int diceCount) {
        var dice = diceUtils.rollDice(diceCount);
        player.setDice(dice);
    }
}
