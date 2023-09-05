package ru.est0y.perudo.services.messaging.test;

import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.Map;

public interface PersonalMessageCreator {
    Map<Player,MessageCreateData> createPersonalMessage(Game game);
}
