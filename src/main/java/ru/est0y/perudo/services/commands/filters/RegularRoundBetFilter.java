package ru.est0y.perudo.services.commands.filters;

import org.springframework.stereotype.Component;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.rounds.RegularRound;
import ru.est0y.perudo.utils.CustomEvent;

@Component
public class RegularRoundBetFilter implements BetFilter<RegularRound> {
    @Override
    public void doFilter(CustomEvent event, Bet oldBet, Bet newBet) {
        if (oldBet.getDiceValue() == 1 && newBet.getDiceValue() == 1) {

        } else if (oldBet.getDiceValue() == 1) {
            oldWasOnes(event, oldBet, newBet);
            return;
        } else if (newBet.getDiceValue() == 1) {
            newHaveOnes(event, oldBet, newBet);
            return;
        }
        var countDiff = newBet.getDiceCount() - oldBet.getDiceCount();
        var valueDiff = newBet.getDiceValue() - oldBet.getDiceValue();
        if (countDiff < 0) {
            event.reply("Количество костей не можен уменьшаться");//.queue();
            throw new RuntimeException();

        }
        if (countDiff == 0 && valueDiff <= 0) {
            event.reply("Либо увеличиваем число кубов,номинал выбираем любой," +
                    "либо оставляем число кубов тем же,но увеличиваем номинал");//.queue();
            throw new RuntimeException();
        }
    }

    private void oldWasOnes(CustomEvent event, Bet oldBet, Bet newBet) {
        var needCounts = (oldBet.getDiceCount() * 2) + 1;
        if (needCounts > newBet.getDiceCount()) {
            event.reply("Количество костей в вашей ставке должно быть равно (x*2)+1," +
                    " где x это количество единицы в прошлой ставке");//.queue();
            throw new RuntimeException();

        }
    }

    private void newHaveOnes(CustomEvent event, Bet oldBet, Bet newBet) {
        var needCounts = (int) Math.ceil((double) oldBet.getDiceCount() / 2);
        ;
        if (needCounts > newBet.getDiceCount()) {
            event.reply("Ставка в единицах может быть меньше только в 2 раза,округление в большую сторну");
                    //.queue();
            throw new RuntimeException();

        }
    }
}
