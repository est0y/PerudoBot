package ru.est0y.perudo.services.commands.move;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.rounds.RegularRound;
import ru.est0y.perudo.domain.rounds.SpecialRound;
import ru.est0y.perudo.services.commands.SlashCommand;
import ru.est0y.perudo.services.commands.filters.BetFilter;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.services.messaging.BetMessageCreator;
import ru.est0y.perudo.services.GameService;
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
    public Mono<Void> execute(SlashCommandInteractionEvent event) {
        //event.getOptions().forEach(optionMapping -> optionMapping.getName());
        int diceCount = event.getOption("count").getAsInt();
        int diceValue = event.getOption("value").getAsInt();
        var customEvent= customEventProducer.produce(event);

        moveCommand.execute(customEvent,diceCount,diceValue);

 /*       var newBet = new Bet(diceCount, diceValue);
        var game = gameService.findByTurnHolder(event.getUser().getIdLong()).blockOptional().orElseThrow(() -> {
            event.reply("Не ваш ход").queue();
            return new RuntimeException();
        });
        if (game.getBelieversCount() == 0) {
            event.reply("Сначало нажми верю/не верю").queue();
            return Mono.empty();
        }
        if (game.isSpecialRound()) {
            specialRoundBetFilter.doFilter(event, game.getLastBet(), newBet);
        } else {
            betFilter.doFilter(event, game.getLastBet(), newBet);
        }

        var bettingPlayer = game.getTurnHolder();
        var nextPlayer = playerUtils.getNextPlayer(game);
        game.setLastBet(new Bet(diceCount, diceValue));
        log.info(bettingPlayer.getName()+" betting "+game.getLastBet());
        *//*var playersIdForSent = game.getPlayers().stream()
                .filter(p -> !p.equals(bettingPlayer))
                .map(Player::getId).toList();*//*
        var betMessage = betMessageCreator.createMessage(game.getTurnHolder(), game.getLastBet());
        Map<Player, MessageCreateData> messagesForPlayer=messagingUtils.getMessageMap(game.getPlayers(),betMessage);
        messagesForPlayer.remove(bettingPlayer);
        messageSender.send(event.getJDA(), messagesForPlayer);
       // messageSender.sendToAll(event.getJDA(), playersIdForSent, betMessage);
        List<ItemComponent> buttons = List.of(Button.success("believe", "Верю"),
                Button.danger("noBelieve", "Не верю"));
        messageSender.send(event.getJDA(), nextPlayer,betMessage,buttons);

        nextTurn(game);
        game.setBelieversCount(0);
        gameService.save(game).subscribe();
        event.reply(betMessage).queue();*/
        return Mono.empty();
    }

    private void nextTurn(Game game) {
        game.setTurnHolder(playerUtils.getNextPlayer(game));
    }



}
