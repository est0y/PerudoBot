package ru.est0y.perudo.services.buttonListeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.services.GameService;
import ru.est0y.perudo.services.RoundManager;
import ru.est0y.perudo.services.messaging.GameStateMessageCreator;
import ru.est0y.perudo.services.messaging.IsBelieveMessageCreator;
import ru.est0y.perudo.services.messaging.MessageSender;
import ru.est0y.perudo.utils.MessagingUtils;

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

    @SuppressWarnings("checkstyle:WhitespaceAround")
    @Override
    public void click(ButtonInteractionEvent event) {
        event.getMessage().delete().queue();

        var game = gameService.findByTurnHolder(event.getUser().getIdLong()).orElseThrow();
        if (game.getBelieversCount() > 0) {
            throw new RuntimeException();
        }
        game.setBelieversCount(game.getBelieversCount() + 1);
        log.info("{} believe", game.getTurnHolder().getName());
        gameService.save(game);
        var message = isBelieveMessageCreator.createMessage(game.getTurnHolder(), false);
        messageSender.send(event.getJDA(), messagingUtils.getMessageMap(game.getPlayers(), message));
        var endRoundMessages = gameStateMessageCreator.createMessage(game);
        messageSender.send(event.getJDA(), endRoundMessages);
        roundManager.finishRound(event.getJDA(), game);
    }
}
