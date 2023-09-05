package ru.est0y.perudo.services.gameCreation;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.utils.MemberUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassicGameCreator implements GameCreator{

    private final PlayerCreator playerCreator;
    private final MemberUtils memberUtils;

    public Mono<Game> createMono(List<Member> members) {
        var players = playerCreator.create(members);
        return Mono.just(new Game(
                null,
                memberUtils.getGuidId(members.get(0)),
                players.get(0),
                players,
                new Bet(0, 0),
                1,
                1,
                false
        ));
    }

    @Override
    public Game create(List<Member> members) {
        var players = playerCreator.create(members);
        return new Game(
                null,
                memberUtils.getGuidId(members.get(0)),
                players.get(0),
                players,
                new Bet(0, 0),
                0,
                1,
                false
        );
    }
}
