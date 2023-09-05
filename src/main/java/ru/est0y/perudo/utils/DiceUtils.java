package ru.est0y.perudo.utils;

import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;

import java.util.List;
import java.util.stream.IntStream;
@Service
public class DiceUtils {
    public List<Integer> rollDice(int count) {
        return IntStream.range(1, count + 1).map(i -> getRandomNumber(1, 6)).boxed().toList();
    }

    public long getDiceValueCountWithOnes(int diceValue, Game game) {

        return game.getPlayers().stream()
                .flatMap(p -> p.getDice().stream())
                .filter(d -> (d == diceValue) || (d == 1)).count();
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
