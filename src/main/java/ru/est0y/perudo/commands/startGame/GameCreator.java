package ru.est0y.perudo.commands.startGame;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.domain.Bet;
import ru.est0y.perudo.domain.Game;
import ru.est0y.perudo.utils.MemberUtils;
import ru.est0y.perudo.utils.SlashCommandInteractionEventUtils;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GameCreator {

    private final PlayerCreator playerCreator;
    private final MemberUtils memberUtils;
    Game create(List<Member> members){
        var players=playerCreator.create(members);
        return new Game(
                null,
                memberUtils.getGuidId(members.get(0)),
                players.get(0),
                players,
                new Bet(0,0),
                0,
                1,
                false
        );
    }
}
