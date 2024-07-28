package ru.est0y.perudo.services.commands.startGame;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

import java.util.List;

public interface MembersFilter {
   void doFilter(List<Member> members, IReplyCallback iReplyCallback);
}
