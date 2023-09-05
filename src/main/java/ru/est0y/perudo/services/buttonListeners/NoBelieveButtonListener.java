package ru.est0y.perudo.services.buttonListeners;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.services.GameService;
import ru.est0y.perudo.services.RoundManager;
import ru.est0y.perudo.services.messaging.GameStateMessageCreator;
import ru.est0y.perudo.services.messaging.MessageSender;

import java.awt.*;

@Service("noBelieve")
@RequiredArgsConstructor
public class NoBelieveButtonListener implements ButtonListener {
    private final GameService gameService;
    private final MessageSender messageSender;
    private final GameStateMessageCreator gameStateMessageCreator;
    private final RoundManager roundManager;

    @Override
    public void click(ButtonInteractionEvent event) {
        event.getMessage().delete().queue();

        var game = gameService.findByTurnHolder(event.getUser().getIdLong()).blockOptional().orElseThrow();
        if (game.getBelieversCount() > 0) throw new RuntimeException();
        game.setBelieversCount(game.getBelieversCount() + 1);
        gameService.save(game).subscribe();

        var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle("**" + game.getTurnHolder().getName() + "**: не верит");
        var message = new MessageCreateBuilder().setEmbeds(embed.build()).build();
        messageSender.sendToAll(event.getJDA(), game.getPlayers().stream()
                        .map(Player::getId).toList(), message);
        messageSender.sendToAll(event.getJDA(), game.getPlayers().stream()
                .map(Player::getId).toList(), gameStateMessageCreator.createMessage(game));
        roundManager.finishRound(event.getJDA(), game);
    }
}
