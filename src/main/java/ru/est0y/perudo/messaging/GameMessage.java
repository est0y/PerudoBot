package ru.est0y.perudo.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.awt.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameMessage implements MessageTemplate {
    private final DiceService diceService;

    @Override
    public MessageCreateData getMessage(Player player, Game game) {
        log.info(game.toString());
        var embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        embed.setTitle("Игроки:");
        game.getPlayers().forEach(p -> {
            String playerString = "";
            playerString = playerString.concat("**"+p.getName()+"**"+": ");
            if (p.equals(player)) {
                playerString = playerString.concat(diceService.getDice(player.getDice()));
            } else {
                playerString = playerString.concat(" have " + p.getDice().size());
            }
            if (p.equals(game.getTurnHolder())) {
                playerString = playerString.concat(":point_left:  ");
            }
            embed.addField("", playerString, false);
        });
        return new MessageCreateBuilder().setEmbeds(embed.build()).build();
    }
}
