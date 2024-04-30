package ru.est0y.perudo.services.messaging;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.utils.DiceUtils;
import ru.est0y.perudo.utils.PlayerUtils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameStateMessageCreatorImpl implements GameStateMessageCreator {

    private final EmojiDiceService emojiDiceService;

    private final PlayerUtils playerUtils;

    private final DiceUtils diceUtils;

    @Override
    public Map<Player, MessageCreateData> createMessage(Game game) {
        var result = new HashMap<Player, MessageCreateData>();
        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle("Конец раунда");
        game.getPlayers().forEach(player -> {
            if (!player.isPlaying()) {
                return;
            }
            String playerString = "";
            playerString = playerString.concat("**" + player.getName() + "**" + ": ");
            playerString = playerString.concat(emojiDiceService.getAsString(player.getDice()));
            embed.addField("", playerString, false);
        });
        var lastBet = game.getLastBet();
        var diceCountInGame = game.isSpecialRound() ?
                diceUtils.getDiceValueCountWithoutOnes(game.getLastBet().getDiceValue(), game) :
                diceUtils.getDiceValueCountWithOnes(game.getLastBet().getDiceValue(), game);
        String loserName;
        if (lastBet.getDiceCount() <= diceCountInGame) {
            loserName = game.getTurnHolder().getName();
        } else {
            loserName = playerUtils.getPreviousPlayer(game).getName();
        }
        embed.addField("", "нужно " + lastBet.getDiceCount() + "" + emojiDiceService.getAsString(lastBet.getDiceValue()) + " ", false);
        embed.addField("", "за столом " + diceCountInGame + "" + emojiDiceService.getAsString(lastBet.getDiceValue()), false);
        embed.addField("", loserName + " теряет кость", false);
        var message = new MessageCreateBuilder().setEmbeds(embed.build()).build();
        return game.getPlayers().stream().collect(Collectors.toMap(player -> player, player -> message));
    }

    @Override
    public Map<Player, MessageCreateData> createPersonalMessage(Game game) {
        var result = new HashMap<Player, MessageCreateData>();
        game.getPlayers().forEach(player -> {
            var embed = new EmbedBuilder();
            embed.setColor(Color.BLACK);
            embed.setTitle("Раунд " + game.getRoundNumber());
            if (game.isSpecialRound()) {
                embed.setTitle("Раунд Мапуто");
            } else {
                embed.setTitle("Раунд " + game.getRoundNumber());
            }
            game.getPlayers().forEach(p -> {
                if (!p.isPlaying()) {
                    return;
                }
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
