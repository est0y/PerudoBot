package ru.est0y.perudo.services.commands.filters;

import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.utils.CustomEvent;

public interface BetFilter<T> {
    void doFilter(CustomEvent event, Bet oldBet, Bet newBet);
}
