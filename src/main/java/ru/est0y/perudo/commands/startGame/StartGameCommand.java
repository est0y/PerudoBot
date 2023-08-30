package ru.est0y.perudo.commands.startGame;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.commands.SlashCommand;
import ru.est0y.perudo.messaging.GameMessagingService;
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
    private final GameCreator gameCreator;
    private final GameMessagingService gameMessagingService;
    private final UserServiceImpl userService;

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var members = utils.getVoiceChannel(event).getMembers();
        membersFilter.doFilter(members);
        var game = gameCreator.create(members);
        try {
            userService.updateOrSave(members.stream().map(ISnowflake::getIdLong).toList()).doOnError(error -> {
                event.reply("An error occurred while saving the game").queue();
                throw new RuntimeException("User data update error", error);
            });

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        gameRepository.save(game).subscribe();

        gameRepository.findByPlayersContaining(game.getTurnHolder()).doOnNext(n -> log.info(n.toString())).subscribe();
      /*  event.getUser().openPrivateChannel().flatMap(channel->channel.sendMessage("b")).queue();
        event.getJDA().retrieveUserById(event.getMember().getIdLong()).flatMap(user -> user.openPrivateChannel().flatMap(channel->channel.sendMessage("jda"))).queue();
   */
        event.reply("Игра началась").queue(v -> gameMessagingService.sendToAll(event.getJDA(), game));
        //gameMessagingService.sendToAll(event.getJDA(),game);
    }

  /*  private void handleGameSaveError(SlashCommandInteractionEvent event, Throwable error) {
        // Handle game save error
        log.error("Error saving game", error);
        event.reply("An error occurred while saving the game").queue();
        throw new RuntimeException("User data update error", error);
    }*/


}
