package ru.est0y.perudo.services.buttonListeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.services.GameService;
import ru.est0y.perudo.services.RoundManager;
import ru.est0y.perudo.services.messaging.test.GameStateMessageCreator;
import ru.est0y.perudo.services.messaging.test.IsBelieveMessageCreator;
import ru.est0y.perudo.services.messaging.test.MessageSender;
import ru.est0y.perudo.utils.MessagingUtils;

import java.awt.*;

@Service("noBelieve")
@RequiredArgsConstructor
@Slf4j
public class NoBelieveButtonListener implements ButtonListener {
    private final GameService gameService;
    private final MessageSender messageSender;
    private final GameStateMessageCreator gameStateMessageCreator;
    private final RoundManager roundManager;
    private final IsBelieveMessageCreator isBelieveMessageCreator;
    private final MessagingUtils messagingUtils;

    @Override
    public void click(ButtonInteractionEvent event) {
        event.getMessage().delete().queue();

        var game = gameService.findByTurnHolder(event.getUser().getIdLong()).blockOptional().orElseThrow();
        if (game.getBelieversCount() > 0) throw new RuntimeException();
        game.setBelieversCount(game.getBelieversCount() + 1);
        log.info(game.getTurnHolder().getName()+" believe");
        gameService.save(game).subscribe();

        /*var embed = new EmbedBuilder();
        embed.setColor(Color.BLACK);
        embed.setTitle("**" + game.getTurnHolder().getName() + "**: не верит");
        var message = new MessageCreateBuilder().setEmbeds(embed.build()).build();*/
        var message=isBelieveMessageCreator.createMessage(game.getTurnHolder(),false);
        messageSender.send(event.getJDA(),messagingUtils.getMessageMap(game.getPlayers(),message));
        var endRoundMessages=gameStateMessageCreator.createMessage(game);
        messageSender.send(event.getJDA(), endRoundMessages);
      /*  messageSender2.sendToAll(event.getJDA(), game.getPlayers().stream()
                        .map(Player::getId).toList(), message);
        messageSender2.sendToAll(event.getJDA(), game.getPlayers().stream()
                .map(Player::getId).toList(), gameStateMessageCreator.createMessage(game));*/
        roundManager.finishRound(event.getJDA(), game);
    }
}
