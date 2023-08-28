package ru.est0y.perudo.messaging;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameMessagingServiceImpl implements GameMessagingService {
    private final GameMessage gameMessage;

    @Override
    public void sendToAll(JDA jda, Game game) {
        game.getPlayers().stream().map(player -> jda.retrieveUserById(player.getId())
                .flatMap(user -> user.openPrivateChannel().
                flatMap(c -> c.sendMessage(gameMessage.getMessage(player, game)))))
                .forEach(RestAction::queue);
    }
}
