package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ru.est0y.perudo.domain.Game;

import java.util.List;

public interface MessageSender {
    void sendToAll(JDA jda, List<Long> ids, MessageCreateData messageCreateData);
    void sendToOne(JDA jda, long id, MessageCreateData messageCreateData);
    void sendToOne(JDA jda, long id, MessageCreateData messageCreateData, List<ItemComponent>buttons);
}
