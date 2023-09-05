package ru.est0y.perudo.services.messaging;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.RestAction;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;

@Service
@RequiredArgsConstructor
public class GameMessagingServiceImpl implements GameMessagingService {
    private final GameStateMessageCreator gameMessage;

    @Override
    public void sendToAll(JDA jda, Game game) {
        game.getPlayers().stream().map(player -> jda.retrieveUserById(player.getId())
                .flatMap(user -> user.openPrivateChannel().
                flatMap(c -> c.sendMessage(gameMessage.createMessage(player, game)))))
                .forEach(RestAction::queue);
    }
}
