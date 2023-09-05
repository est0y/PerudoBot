package ru.est0y.perudo.services.messaging.test;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Map;

public interface MessageSender {
    void send(JDA jda, Map<Player, MessageCreateData> messages);
    void send(JDA jda,Player player,MessageCreateData message);
    void send(JDA jda, Player player, MessageCreateData messageCreateData, List<ItemComponent> buttons);

}
