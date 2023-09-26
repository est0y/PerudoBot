package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.Map;

public interface MessageCreator{
    Map<Player,MessageCreateData> createMessage(Game game);
}
