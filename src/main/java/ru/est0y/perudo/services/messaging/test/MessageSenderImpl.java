package ru.est0y.perudo.services.messaging.test;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.RestRateLimiter;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Service
@Slf4j
public class MessageSenderImpl implements MessageSender {
    @Override
    public void send(JDA jda, Map<Player, MessageCreateData> messages) {
        //todo починить
        /*messages.keySet().stream().map(player -> jda.getUserById(player.getId()).openPrivateChannel()
                .flatMap(c->c.sendMessage(messages.get(player))).onErrorFlatMap(error->{
                    log.error("Sending to All many error:",error);
                    return null;
                })).forEach(RestAction::queue);*/
                //messages.keySet().stream().map(player ->jda.retrieveUserById(player.getId()).flatMap(user -> user.openPrivateChannel())).);
        messages.keySet().stream().map(player -> jda.retrieveUserById(player.getId())
                        .flatMap(user -> user.openPrivateChannel().
                                flatMap(c -> c.sendMessage(messages.get(player)))))
                .forEach(RestAction::queue);
        log.info("send to many "+messages);
    }

    @Override
    public void send(JDA jda, Player player, MessageCreateData message) {
        jda.getUserById(player.getId()).openPrivateChannel().flatMap(c->c.sendMessage(message))
                .onErrorFlatMap(error->{
                    log.error("Sending to one error:",error);
                    return null;
                })
                .queue();
        //jda.getUserById(player.getId()).openPrivateChannel().flatMap(c->c.sendMessage(message)).queue();
       /* jda.retrieveUserById(player.getId())
                .flatMap(user -> user.openPrivateChannel()
                        .flatMap(c->c.sendMessage(message))).queue();*/
        log.info("personal message "+player.getName());
    }

    @Override
    public void send(JDA jda, Player player, MessageCreateData message, List<ItemComponent> buttons) {
        //new RestRateLimiter()
        jda.getUserById(player.getId()).openPrivateChannel().flatMap(c->c.sendMessage(message).addActionRow(buttons)).onErrorFlatMap(error->{
            log.error("Sending with buttons error:",error);
            return null;
        }).queue();
       // jda.retrieveUserById(player.getId()).queue(user ->user.openPrivateChannel().queue(c->c.sendMessage(message).addActionRow(buttons).queue()));
                //.flatMap(user -> user.openPrivateChannel()
                        //.flatMap(c->c.sendMessage(message).addActionRow(buttons))).queue();
        log.info("send believe/not believe buttons for "+player.getName());
    }
}
