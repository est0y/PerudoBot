package ru.est0y.perudo.services.gameCreation;

import net.dv8tion.jda.api.entities.Member;
import ru.est0y.perudo.domain.Game;

import java.util.List;

public interface GameCreator {
    Game create(List<Member> members);
}
