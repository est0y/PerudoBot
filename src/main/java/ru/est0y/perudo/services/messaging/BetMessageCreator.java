package ru.est0y.perudo.services.messaging;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.awt.*;
@Service
@RequiredArgsConstructor
public class BetMessageCreator  {
    private final EmojiDiceService emojiDiceService;


    public MessageCreateData createMessage(Player player, Bet bet) {

        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle(player.getName() + ": **"+bet.getDiceCount()+"**"+ emojiDiceService.getAsString(bet.getDiceValue()));
        return new MessageCreateBuilder().setEmbeds(embed.build()).build();
    }
}
