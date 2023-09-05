package ru.est0y.perudo.services.messaging;

import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DiceService {
    private final static Map<Integer, String> map = Map.of(
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
            result = result.concat(map.get(die));
        }
        return result;
    }

    String getDice(int dice) {
        return map.get(dice);
    }
}
