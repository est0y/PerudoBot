package ru.est0y.perudo.commands.move;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.commands.SlashCommand;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.services.GameService;
@Service("move")
@RequiredArgsConstructor
@Slf4j
public class MoveCommand implements SlashCommand {
   private final GameService gameService;

    //проверка на что ща его ход
    //
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        int diceCount=event.getOption("count").getAsInt();
        int diceValue=event.getOption("value").getAsInt();

        var game=gameService.findByTurnHolder(event.getUser().getIdLong()).blockOptional().orElseThrow();
        var playerNumber=game.getTurnHolder().getNumber();
        var nextPlayer=playerNumber==game.getPlayers().size()?game.getPlayers().get(0):game.getPlayers().get(playerNumber);
        log.info(nextPlayer.toString());
        game.setTurnHolder(nextPlayer);
        game.setLastBet(new Bet(diceCount,diceValue));
        gameService.save(game).subscribe();
        event.reply("all ok").queue();
    }
    private Mono<Boolean> filter(){
        return Mono.just(false);
    }
}
