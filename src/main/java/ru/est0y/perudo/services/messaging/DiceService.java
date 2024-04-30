package ru.est0y.perudo.services.messaging;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DiceService {

    private static final Map<Integer, String> MAP = Map.of(
            1, ":one:",
            2, ":two:",
            3, ":three:",
            4, ":four:",
            5, ":five:",
            6, ":six:"
    );

    String getDice(List<Integer> dice) {
        String result = "";
        for (var die : dice) {
            result = result.concat(MAP.get(die));
        }
        return result;
    }

    String getDice(int dice) {
        return MAP.get(dice);
    }
}
