package ru.est0y.perudo.utils;

import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberUtils {
    public long getUserId(Member member) {
        return member.getIdLong();
    }

    public long getGuidId(Member member) {
        return member.getGuild().getIdLong();
    }
}
