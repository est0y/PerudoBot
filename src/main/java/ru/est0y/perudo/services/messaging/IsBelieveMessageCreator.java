package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.awt.Color;

@Service
public class IsBelieveMessageCreator {
    public MessageCreateData createMessage(Player player, boolean isBelieve) {
        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        var value = isBelieve ? "верит" : "не верит";
        embed.setTitle("**" + player.getName() + "**: " + value);
        return new MessageCreateBuilder().setEmbeds(embed.build()).build();
    }
}
