package ru.est0y.perudo.services.buttonListeners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.services.GameService;
import ru.est0y.perudo.services.messaging.IsBelieveMessageCreator;
import ru.est0y.perudo.services.messaging.MessageSender;
import ru.est0y.perudo.utils.MessagingUtils;

@Service("believe")
@RequiredArgsConstructor
@Slf4j
public class BelieveButtonListener implements ButtonListener {

    private final GameService gameService;

    private final IsBelieveMessageCreator isBelieveMessageCreator;

    private final MessageSender messageSender;

    private final MessagingUtils messagingUtils;

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
        var message = isBelieveMessageCreator.createMessage(game.getTurnHolder(), true);
        messageSender.send(event.getJDA(), messagingUtils.getMessageMap(game.getPlayers(), message));
    }
}
