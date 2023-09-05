package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

public interface PrivateMessageCreator {
    MessageCreateData createMessage(Player player, Game game);
}
