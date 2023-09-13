package ru.est0y.perudo.services.messaging.test;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.Player;

import java.awt.*;
@Service
public class GameEndMessageCreator {
    public MessageCreateData createMessage(Player player) {

        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle("Конец игры");
        embed.addField("","**"+player.getName()+" победил**",false);
        //embed.setTitle(player.getName() + ": **"+bet.getDiceCount()+"**"+ emojiDiceService.getAsString(bet.getDiceValue()));
        return new MessageCreateBuilder().setEmbeds(embed.build()).build();
    }
}
