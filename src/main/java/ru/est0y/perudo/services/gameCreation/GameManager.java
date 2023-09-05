package ru.est0y.perudo.services.gameCreation;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Game;

import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class GameManager {
    private final Map<String,GameCreator> gameCreatorMap;
    public Game getGame(String creatorName, List<Member>members){
        return gameCreatorMap.get(creatorName).create(members);
    }
}
