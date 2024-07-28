package ru.est0y.perudo.utils;

import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessagingUtils {
    public Map<Player, MessageCreateData> getMessageMap(List<Player> players, MessageCreateData message) {
        return players.stream().collect(Collectors.toMap(player -> player, player -> message));
    }
}
