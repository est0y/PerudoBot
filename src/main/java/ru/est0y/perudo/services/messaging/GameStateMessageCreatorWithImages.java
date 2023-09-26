package ru.est0y.perudo.services.messaging;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import reactor.core.publisher.Flux;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class GameStateMessageCreatorWithImages  {
    private final GameStateMessageCreator gameStateMessageCreator;


    public Flux<RestAction<Message>> createMessage(JDA jda,Map<Player,MessageCreateData> map) {
        //var map =gameStateMessageCreator.createMessage(game);
       return Flux.fromStream( map.keySet().stream().map(player -> jda.retrieveUserById(player.getId()).flatMap(user -> user.openPrivateChannel().flatMap(c->c.sendMessage(map.get(player))))));

      //return   map.keySet().stream().map(player -> jda.retrieveUserById(player.getId()).flatMap(user -> user.openPrivateChannel().flatMap(c->c.sendMessage(map.get(player))))).toList();
    }


    public Map<Player, MessageCreateData> createPersonalMessage(Game game) {
        return null;
    }
}
