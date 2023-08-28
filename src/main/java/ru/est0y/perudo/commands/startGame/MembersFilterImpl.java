package ru.est0y.perudo.commands.startGame;

import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class MembersFilterImpl implements MembersFilter {
    @Override
    public void doFilter(List<Member> members) {
        if (members.size() < 1) {
            throw new RuntimeException();
        }
        if (false){//если игроки уже писутвуют в игре
            throw new RuntimeException();
        }
    }
}
