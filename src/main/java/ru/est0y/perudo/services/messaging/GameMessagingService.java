package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.JDA;
import ru.est0y.perudo.domain.Game;

public interface GameMessagingService {
    void sendToAll(JDA jda, Game game);
}
