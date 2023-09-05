package ru.est0y.perudo.services.commands.startGame;

import net.dv8tion.jda.api.entities.Member;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MembersFilter {
   Mono<Void> doFilter(Flux<Member> members);
}
