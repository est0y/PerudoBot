package ru.est0y.perudo.services.messaging.test;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.services.messaging.EmojiDiceService;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameStateMessageCreatorImpl implements GameStateMessageCreator {
    private final EmojiDiceService emojiDiceService;

    @Override
    public Map<Player,MessageCreateData> createMessage(Game game) {
        var result = new HashMap<Player, MessageCreateData>();
        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle("Игроки:");
        game.getPlayers().forEach(player -> {
            String playerString = "";
            playerString = playerString.concat("**" + player.getName() + "**" + ": ");
            playerString = playerString.concat(emojiDiceService.getAsString(player.getDice()));
            if (player.equals(game.getTurnHolder())) {
                playerString = playerString.concat(":point_left:  ");
            }
            embed.addField("", playerString, false);
        });
        var diceCount = game.getPlayers().stream().flatMap(p -> p.getDice().stream()).count();
        embed.addField("", "Всего костей за столом: " + diceCount, false);
       var message= new MessageCreateBuilder().setEmbeds(embed.build()).build();
       return game.getPlayers().stream().collect(Collectors.toMap(player->player,player->message));
    }

    @Override
    public Map<Player, MessageCreateData> createPersonalMessage(Game game) {
        var result = new HashMap<Player, MessageCreateData>();
        game.getPlayers().forEach(player -> {
            var embed = new EmbedBuilder();
            embed.setColor(Color.BLACK);
            embed.setTitle("Игроки:");
            game.getPlayers().forEach(p -> {
                String playerString = "";
                playerString = playerString.concat("**" + p.getName() + "**" + ": ");
                if (p.equals(player)) {
                    playerString = playerString.concat(emojiDiceService.getAsString(p.getDice()));
                } else {
                    playerString = playerString.concat(" имеет " + p.getDice().size() + " костей");
                }
                if (p.equals(game.getTurnHolder())) {
                    playerString = playerString.concat(":point_left:  ");
                }
                embed.addField("", playerString, false);
            });
            var diceCount = game.getPlayers().stream().flatMap(p -> p.getDice().stream()).count();
            embed.addField("", "Всего костей за столом: " + diceCount, false);
            var message = new MessageCreateBuilder().setEmbeds(embed.build()).build();
            result.put(player, message);
        });

        return result;
    }
}
