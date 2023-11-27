package ru.est0y.perudo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.services.messaging.GameEndMessageCreator;
import ru.est0y.perudo.services.messaging.GameStateMessageCreator;
import ru.est0y.perudo.services.messaging.MessageSender;
import ru.est0y.perudo.utils.DiceUtils;
import ru.est0y.perudo.utils.MessagingUtils;
import ru.est0y.perudo.utils.PlayerUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoundManager {
    private final PlayerUtils playerUtils;
    private final DiceUtils diceUtils;
    private final GameService gameService;
    private final GameStateMessageCreator gameStateMessageCreator;
    private final MessageSender messageSender;
    private final GameEndMessageCreator gameEndMessageCreator;
    private final MessagingUtils messagingUtils;
    private final UserServiceImpl userService;

    //private final GameStateMessageCreator gameStateMessageCreator;
    //private final MessageSender messageSender;
    @Transactional
    public void finishRound(JDA jda, Game game) {
        var lastBet = game.getLastBet();
        log.info("bet=" + lastBet.getDiceCount() + " " + lastBet.getDiceValue());
        var diceValueCount = diceUtils.getDiceValueCountWithOnes(lastBet.getDiceValue(), game);
        var diceValueWithoutOnes = diceUtils.getDiceValueCountWithoutOnes(lastBet.getDiceValue(), game);
        var betPlayer = playerUtils.getPreviousPlayer(game);
        log.info("bet Payer = " + betPlayer.getName());
        //todo исправить
        var noBeliever = game.getPlayers().stream().filter(player -> player.getNumber() == game.getTurnHolder().getNumber()).findFirst().orElseThrow();
        log.info("noBeliever=" + noBeliever.getName());
        Player loser;
        if (game.isSpecialRound()) {
            log.info("matching dice with ones on the table count = " + diceValueWithoutOnes);
            loser = lastBet.getDiceCount() <= diceValueWithoutOnes ? noBeliever : betPlayer;
        } else {
            log.info("matching dice on the table count = " + diceValueCount);
            loser = lastBet.getDiceCount() <= diceValueCount ? noBeliever : betPlayer;
        }
        log.info("Loser name "+loser.getName());
        //var loser = lastBet.getDiceCount() <= diceValueCount ? noBeliever : betPlayer;
        var newLoserDiceCount = loser.getDice().size() - 1;
        playerUtils.rollDice(loser, newLoserDiceCount);
        game.getPlayers().stream().filter(p -> !p.equals(loser)).forEach(p -> playerUtils.rollDice(p, p.getDice().size()));
        if (game.isSpecialRound()) {
            game.setSpecialRound(false);
        }
        if (newLoserDiceCount == 1) {
            game.setSpecialRound(true);
        }
        game.setLastBet(new Bet(0, 0));
        log.info(game.getLastBet().toString());
        game.setTurnHolder(loser);
        if (newLoserDiceCount == 0) {
            var playersHaveMoreOneDice = game.getPlayers().stream().filter(p -> p.getDice().size() > 0).toList();
            if (playersHaveMoreOneDice.size() == 1) {
                log.info("end game ");
                endGame(jda, game, playersHaveMoreOneDice.get(0));
                return;
            }
            loser.setPlaying(false);
            game.setTurnHolder(playerUtils.getNextPlayer(game));
        }
        game.setRoundNumber(game.getRoundNumber() + 1);
        gameService.save(game);
        messageSender.send(jda, gameStateMessageCreator.createPersonalMessage(game));
        //game.getPlayers().forEach(p -> messageSender.sendToOne(jda, p.getId(), gameStateMessageCreator.createMessage(p, game)));

    }

    private void endGame(JDA jda, Game game, Player player) {
        var message = gameEndMessageCreator.createMessage(player);
        messageSender.send(jda, messagingUtils.getMessageMap(game.getPlayers(), message));
        userService.updateIsPlayingByIds(game.getPlayers().stream().map(Player::getId).toList(), false);
        gameService.delete(game);
    }

}
