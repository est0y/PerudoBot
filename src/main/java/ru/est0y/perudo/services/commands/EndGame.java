package ru.est0y.perudo.services.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.est0y.perudo.domain.Player;
import ru.est0y.perudo.repositories.UserRepository;
import ru.est0y.perudo.services.GameService;
import ru.est0y.perudo.services.UserServiceImpl;

@Service("endgame")
@RequiredArgsConstructor
@Slf4j
public class EndGame implements SlashCommand {
    private final GameService gameService;
    private final UserServiceImpl userService;
    @Transactional
    @Override
    public Mono<Void> execute(SlashCommandInteractionEvent event) {
        var game = gameService.getGameByPlayer(event.getUser().getIdLong()).blockOptional().orElseThrow(() -> {
            event.reply("У тебя нет активных игр").queue();
            return new RuntimeException();
        });
      gameService.delete(game).subscribe();
      userService.updateIsPlayingByIds(game.getPlayers().stream().map(Player::getId).toList(),false);
      event.reply("Игра закончена").queue();
        log.info("Игра досрочно закончена");
        return Mono.empty();
    }
}
