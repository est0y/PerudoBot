package ru.est0y.perudo.services.commands.startGame;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MembersFilterImpl implements MembersFilter {
    @Override
    public void doFilter(List<Member> members) {
        log.info("filter Method");
        if (members.size() < 2) {
            throw new RuntimeException("Not enough members");
        }
    }
}
