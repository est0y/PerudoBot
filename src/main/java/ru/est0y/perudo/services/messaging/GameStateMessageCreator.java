package ru.est0y.perudo.services.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.utils.PlayerUtils;

import java.awt.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameStateMessageCreator implements PrivateMessageCreator {
    private final EmojiDiceService emojiDiceService;
    private final PlayerUtils playerUtils;

    @Override
    public MessageCreateData createMessage(Player player, Game game) {
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
            //.setImage("https://cdn.discordapp.com/attachments/1144968143056404510/1146975622439121017/image_processing20210613-2832-dt5kqc.png");
        });
        var diceCount = game.getPlayers().stream().flatMap(p -> p.getDice().stream()).count();
        embed.addField("", "Всего костей за столом: " + diceCount, false);
        return new MessageCreateBuilder().setEmbeds(embed.build()).build();
    }

    public MessageCreateData createMessage(Game game) {
        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle("Игроки:");
        game.getPlayers().forEach(p -> {
            String playerString = "";
            playerString = playerString.concat("**" + p.getName() + "**" + ": ");
            playerString = playerString.concat(emojiDiceService.getAsString(p.getDice()));
            if (p.equals(game.getTurnHolder())) {
                playerString = playerString.concat(":point_left:  ");
            }
            embed.addField("", playerString, false);
        });
        var lastBet = game.getLastBet();
        var diceCountInGame = game.getPlayers().stream().flatMap(p -> p.getDice().stream()).filter(d -> (lastBet.getDiceValue() == d) || d == 1).count();
        String loserName;
        if (lastBet.getDiceCount() <= diceCountInGame) {
            loserName = game.getTurnHolder().getName();
        } else {
            loserName = playerUtils.getPreviousPlayer(game).getName();
        }
        embed.addField("", "нужно " + lastBet.getDiceCount() + "" + emojiDiceService.getAsString(lastBet.getDiceValue()) + " ", false);
        embed.addField("", "за столом "+ diceCountInGame + "" + emojiDiceService.getAsString(lastBet.getDiceValue()) , false);
        embed.addField("", loserName + " теряет кость", false);
        return new MessageCreateBuilder().setEmbeds(embed.build()).build();
    }

/*    private Player previousPlayer(Game game) {
        var turnHolderNum = game.getTurnHolder().getNumber();
        return turnHolderNum == 1 ? game.getPlayers().get(game.getPlayers().size() - 1) : game.getPlayers().get(turnHolderNum - 2);
    }*/
}
