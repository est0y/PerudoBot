package ru.est0y.perudo.services.commands.move;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.rounds.RegularRound;
import ru.est0y.perudo.domain.rounds.SpecialRound;
import ru.est0y.perudo.services.GameService;
import ru.est0y.perudo.services.commands.SlashCommand;
import ru.est0y.perudo.services.commands.filters.BetFilter;
import ru.est0y.perudo.services.messaging.BetMessageCreator;
import ru.est0y.perudo.services.messaging.MessageSender;
import ru.est0y.perudo.utils.CustomEventProducer;
import ru.est0y.perudo.utils.MessagingUtils;
import ru.est0y.perudo.utils.PlayerUtils;

@Service("move")
@RequiredArgsConstructor
@Slf4j
public class MoveSlashCommand implements SlashCommand {

    private final GameService gameService;

    private final BetFilter<RegularRound> betFilter;

    private final BetFilter<SpecialRound> specialRoundBetFilter;

    private final BetMessageCreator betMessageCreator;

    private final MessageSender messageSender;

    private final PlayerUtils playerUtils;

    private final MessagingUtils messagingUtils;

    private final CustomEventProducer customEventProducer;

    private final MoveCommand moveCommand;

    @Transactional
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        int diceCount = event.getOption("count").getAsInt();
        int diceValue = event.getOption("value").getAsInt();
        var customEvent = customEventProducer.produce(event);
        moveCommand.execute(customEvent, diceCount, diceValue);
    }

    private void nextTurn(Game game) {
        game.setTurnHolder(playerUtils.getNextPlayer(game));
    }


}
