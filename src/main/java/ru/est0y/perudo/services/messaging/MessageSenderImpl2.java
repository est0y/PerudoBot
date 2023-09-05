package ru.est0y.perudo.services.messaging;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageSenderImpl2 implements MessageSender {
    @Override
    public void sendToAll(JDA jda, List<Long> ids, MessageCreateData messageCreateData) {
        ids.stream().map(id -> jda.retrieveUserById(id)
                        .flatMap(user -> user.openPrivateChannel().
                                flatMap(c -> c.sendMessage(messageCreateData))))
                .forEach(RestAction::queue);
    }

    @Override
    public void sendToOne(JDA jda, long id, MessageCreateData messageCreateData) {
        jda.retrieveUserById(id)
                .flatMap(user -> user.openPrivateChannel().
                        flatMap(c -> c.sendMessage(messageCreateData))).queue();
    }

    @Override
    public void sendToOne(JDA jda, long id, MessageCreateData messageCreateData, List<ItemComponent> buttons) {
        jda.retrieveUserById(id)
                .flatMap(user -> user.openPrivateChannel().
                        flatMap(c -> c.sendMessage(messageCreateData).addActionRow(buttons))).queue();
    }
}
