package ru.est0y.perudo.services.commands.startGame;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MembersFilterImpl implements MembersFilter {
    @Override
    public Mono<Void> doFilter(Flux<Member> members) {
        log.info("filter Method");
        return members
               .count()
               .flatMap(size -> {
                   if (size < 2) {
                       return Mono.error(new RuntimeException("No members found."));
                   } else {
                       return Mono.empty();
                   }
               });
    }
}
