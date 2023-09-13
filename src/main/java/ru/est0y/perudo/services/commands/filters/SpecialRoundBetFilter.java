package ru.est0y.perudo.services.commands.filters;

import org.springframework.stereotype.Component;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.rounds.SpecialRound;
import ru.est0y.perudo.utils.CustomEvent;

@Component
public class SpecialRoundBetFilter implements BetFilter<SpecialRound> {
    @Override
    public void doFilter(CustomEvent event, Bet oldBet, Bet newBet) {
        // int diceCount = event.getOption("count").getAsInt();
        // int diceValue = event.getOption("value").getAsInt();
        int diceCount = newBet.getDiceCount();
        int diceValue = newBet.getDiceValue();
        if (oldBet.getDiceCount() == 0 && oldBet.getDiceValue() == 0) return;
        if (oldBet.getDiceValue() != diceValue) {
            event.reply("В рауде Мапуто нельзя менять номинал кости");//.queue();
            throw new RuntimeException();
        }
        if (diceCount <= oldBet.getDiceCount()) {
            event.reply("В рауде Мапуто число костей должно только увеличиваться");//.queue();
        }

    }
}
