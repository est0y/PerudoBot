package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDAInfo;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.services.messaging.test.GameStateMessageCreator;
import ru.est0y.perudo.services.messaging.test.MessageSender;
import ru.est0y.perudo.utils.DiceUtils;
import ru.est0y.perudo.utils.PlayerUtils;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoundManager {
    private final PlayerUtils playerUtils;
    private final DiceUtils diceUtils;
    private final GameService gameService;
    private final GameStateMessageCreator gameStateMessageCreator;
    private final MessageSender messageSender;
    //private final GameStateMessageCreator gameStateMessageCreator;
    //private final MessageSender messageSender;

    public void finishRound(JDA jda, Game game) {
        var lastBet = game.getLastBet();
        log.info("bet=" + lastBet.getDiceCount() + " " + lastBet.getDiceValue());
        var diceValueCount = diceUtils.getDiceValueCountWithOnes(lastBet.getDiceValue(), game);
        log.info("needDiceCount=" + diceValueCount);
        var betPlayer = playerUtils.getPreviousPlayer(game);
        log.info("betPayer=" + betPlayer.getName());
        //todo исправить
        var noBeliever = game.getPlayers().stream().filter(player -> player.getNumber()==game.getTurnHolder().getNumber()).findFirst().orElseThrow();
        log.info("noBeliever=" + noBeliever.getName());
        var loser = lastBet.getDiceCount() <= diceValueCount ? noBeliever : betPlayer;
        var newLoserDiceCount = loser.getDice().size() - 1;
        playerUtils.rollDice(loser, newLoserDiceCount);
        if (newLoserDiceCount == 1) {
            game.setSpecialRound(true);
        }
        game.getPlayers().stream().filter(p -> !p.equals(loser)).forEach(p -> playerUtils.rollDice(p, p.getDice().size()));
        if (game.isSpecialRound()) {
            game.setSpecialRound(false);
        }
        game.setLastBet(new Bet(0, 0));
        log.info(game.getLastBet().toString());
        game.setTurnHolder(loser);
        if (newLoserDiceCount == 0) {
           var playersHaveMoreOneDice= game.getPlayers().stream().filter(p->p.getDice().size()>0).toList();
           if (playersHaveMoreOneDice.size()==1){
               log.info("end game ");
               endGame(game,playersHaveMoreOneDice.get(0));
               return;
           }
           loser.setPlaying(false);
           game.setTurnHolder(playerUtils.getNextPlayer(game));
        }
        game.setRoundNumber(game.getRoundNumber() + 1);
        gameService.save(game).subscribe();
        messageSender.send(jda,gameStateMessageCreator.createPersonalMessage(game));
        //game.getPlayers().forEach(p -> messageSender.sendToOne(jda, p.getId(), gameStateMessageCreator.createMessage(p, game)));

    }

    private void endGame(Game game, Player player) {
        gameService.delete(game).subscribe();
    }

}
