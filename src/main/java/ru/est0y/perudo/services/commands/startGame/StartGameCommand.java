package ru.est0y.perudo.services.commands.startGame;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.services.commands.SlashCommand;
import ru.est0y.perudo.services.gameCreation.ClassicGameCreator;
import ru.est0y.perudo.services.messaging.GameMessagingService;
import ru.est0y.perudo.repositories.GameRepository;
import ru.est0y.perudo.repositories.PlayerRepository;
import ru.est0y.perudo.repositories.UserRepository;
import ru.est0y.perudo.services.UserServiceImpl;
import ru.est0y.perudo.utils.SlashCommandInteractionEventUtils;

@Slf4j
@Service("sg")
@RequiredArgsConstructor
public class StartGameCommand implements SlashCommand {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final SlashCommandInteractionEventUtils utils;
    private final MembersFilter membersFilter;
    private final ClassicGameCreator gameCreator;
    private final GameMessagingService gameMessagingService;
    private final UserServiceImpl userService;

    @Override
    public Mono<Void> execute(SlashCommandInteractionEvent event) {
        var members = utils.getVoiceChannel(event).getMembers().stream().filter(m->!m.getUser().isBot()).toList();
        return membersFilter.doFilter(Flux.fromIterable(members)).thenMany(
                        userService.updateOrSave(members.stream().map(ISnowflake::getIdLong).toList())
                                .doOnError((e) -> event.reply("Кто-то из игроков уже в игре").queue())
                ).then(gameCreator.createMono(members)).flatMap(gameRepository::save).flatMap(game -> Mono.fromRunnable(() ->
                        event.reply("Игра началась").queue(v -> gameMessagingService.sendToAll(event.getJDA(), game))))
                .then();

    }


}
