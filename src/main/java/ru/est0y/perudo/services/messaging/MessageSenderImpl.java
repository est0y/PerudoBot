package ru.est0y.perudo.services.messaging;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageSenderImpl implements MessageSender {
    @Retryable
    @Override
    public void send(JDA jda, Map<Player, MessageCreateData> messages) {
        //todo починить
        /*messages.keySet().stream().map(player -> jda.getUserById(player.getId()).openPrivateChannel()
                .flatMap(c->c.sendMessage(messages.get(player))).onErrorFlatMap(error->{
                    log.error("Sending to All many error:",error);
                    return null;
                })).forEach(RestAction::queue);*/
                //messages.keySet().stream().map(player ->jda.retrieveUserById(player.getId()).flatMap(user -> user.openPrivateChannel())).);



        messages.keySet().forEach(player -> sendMessageWithRetry(jda,player,messages.get(player),List.of()));
  /*      messages.keySet().stream().map(player -> jda.retrieveUserById(player.getId())
                        .flatMap(user -> user.openPrivateChannel().
                                flatMap(c -> c.sendMessage(messages.get(player)))))
                .forEach(RestAction::queue);*/
        log.info("send to many "+messages);
    }
    @Retryable
    @Override
    public void send(JDA jda, Player player, MessageCreateData message) {
        sendMessageWithRetry(jda,player,message,List.of());
       /* jda.getUserById(player.getId()).openPrivateChannel().flatMap(c->c.sendMessage(message))
                .onErrorFlatMap(error->{
                    log.error("Sending to one error:",error);
                    return null;
                })
                .queue();*/
        //jda.getUserById(player.getId()).openPrivateChannel().flatMap(c->c.sendMessage(message)).queue();
       /* jda.retrieveUserById(player.getId())
                .flatMap(user -> user.openPrivateChannel()
                        .flatMap(c->c.sendMessage(message))).queue();*/
        log.info("personal message "+player.getName());
    }

    @Override
    public void send(JDA jda, Player player, MessageCreateData message, List<ItemComponent> buttons) {
        sendMessageWithRetry(jda,player,message,buttons);
        //new RestRateLimiter()
       /*.onErrorFlatMap(error->{
            log.error("Sending with buttons error:",error);
            return error;
        })*/
       // jda.retrieveUserById(player.getId()).queue(user ->user.openPrivateChannel().queue(c->c.sendMessage(message).addActionRow(buttons).queue()));
                //.flatMap(user -> user.openPrivateChannel()
                        //.flatMap(c->c.sendMessage(message).addActionRow(buttons))).queue();
        log.info("send believe/not believe buttons for "+player.getName());
    }


public static void sendMessageWithRetry(JDA jda, Player player,MessageCreateData message, List<ItemComponent> buttons) {
    log.info("method");

        try {
            jda.retrieveUserById(player.getId()).queue(user -> {
                 user.openPrivateChannel().queue(channel -> {
                     if (buttons.size()==0){
                         channel.sendMessage(message).queue();
                     }else {
                         channel.sendMessage(message).addActionRow(buttons).queue();
                     }
                 });
            });
        } catch (Exception e) {
            log.info("send message with button error");
            log.error("send message error",e);
            throw e;
        }
}
}

