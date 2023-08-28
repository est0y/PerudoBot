package ru.est0y.perudo.messaging;

import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

public interface MessageTemplate {
    MessageCreateData getMessage(Player player,Game game);
}
