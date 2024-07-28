package ru.est0y.perudo.services.commands.startGame;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MembersFilterImpl implements MembersFilter {
    private static void accept(InteractionHook a) {
        throw new RuntimeException("Not enough members");
    }

    @Override
    public void doFilter(List<Member> members, IReplyCallback iReplyCallback) {
        if (members.size() < 2) {
            log.info("YES");
            iReplyCallback.reply("Недостаточное количество игроков").queue(MembersFilterImpl::accept);
        }
    }
}
