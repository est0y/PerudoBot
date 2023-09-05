package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmojiDiceService {
    private final static Map<Integer, String> codes = Map.of(
            1, "<:dice1:1146951057608552478>",
            2, "<:dice2:1146953458247807077>",
            3, "<:dice3:1146953487591153685>",
            4, "<:dice4:1146953490749456536>",
            5, "<:dice5:1146953492511084644>",
            6, "<:dice6:1146953494885056553>"
    );

    public EmojiUnion getEmoji(int diceNum) {
        return Emoji.fromFormatted(codes.get(diceNum));
    }

    public String getAsString(int diceNum) {
        return codes.get(diceNum);
    }

    public String getAsString(List<Integer> dice) {
        String result = "";
        for (var die : dice) {
            result = result.concat(getAsString(die));
        }
        return result;
    }
}
