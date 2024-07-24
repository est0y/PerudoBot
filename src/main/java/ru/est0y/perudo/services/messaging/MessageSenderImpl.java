package ru.est0y.perudo.services.messaging;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageSenderImpl implements MessageSender {
    @Retryable
    @Override
    public void send(JDA jda, Map<Player, MessageCreateData> messages) {
        //todo починить
        messages.keySet().forEach(player -> sendMessageWithRetry(jda, player, messages.get(player), List.of()));
        log.info("send to many {}", messages);
    }

    @Retryable
    @Override
    public void send(JDA jda, Player player, MessageCreateData message) {
        sendMessageWithRetry(jda, player, message, List.of());
        log.info("personal message {}", player.getName());
    }

    @Retryable
    @Override
    public void send(JDA jda, Player player, MessageCreateData message, List<ItemComponent> buttons) {
        sendMessageWithRetry(jda, player, message, buttons);
        log.info("send believe/not believe buttons for {}", player.getName());
    }


    public static void sendMessageWithRetry(JDA jda, Player player,
                                            MessageCreateData message,
                                            List<ItemComponent> buttons) {
            jda.retrieveUserById(player.getId()).queue(user -> {
                user.openPrivateChannel().queue(channel -> {
                    if (buttons.isEmpty()) {
                        channel.sendMessage(message).queue();
                    } else {
                        channel.sendMessage(message).addActionRow(buttons).queue();
                    }
                });
            });
    }
}

