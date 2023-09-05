package ru.est0y.perudo.services.commands.filters;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import ru.est0y.perudo.domain.Bet;

public interface BetFilter<ROUND_TYPE> {
    void doFilter(SlashCommandInteractionEvent event,Bet oldBet, Bet newBet);
}
