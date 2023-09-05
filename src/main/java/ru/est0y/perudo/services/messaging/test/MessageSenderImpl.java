package ru.est0y.perudo.services.messaging.test;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Map;
@Service
public class MessageSenderImpl implements MessageSender {
    @Override
    public void send(JDA jda, Map<Player, MessageCreateData> messages) {
        messages.keySet().stream().map(player -> jda.retrieveUserById(player.getId())
                        .flatMap(user -> user.openPrivateChannel().
                                flatMap(c -> c.sendMessage(messages.get(player)))))
                .forEach(RestAction::queue);
    }

    @Override
    public void send(JDA jda, Player player, MessageCreateData message) {
        jda.retrieveUserById(player.getId())
                .flatMap(user -> user.openPrivateChannel()
                        .flatMap(c->c.sendMessage(message))).queue();
    }

    @Override
    public void send(JDA jda, Player player, MessageCreateData message, List<ItemComponent> buttons) {
        jda.retrieveUserById(player.getId())
                .flatMap(user -> user.openPrivateChannel()
                        .flatMap(c->c.sendMessage(message).addActionRow(buttons))).queue();
    }
}
