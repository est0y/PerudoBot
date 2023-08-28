package ru.est0y.perudo.commands.startGame;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import ru.est0y.perudo.commands.SlashCommand;
import ru.est0y.perudo.messaging.GameMessagingService;
import ru.est0y.perudo.repositories.GameRepository;
import ru.est0y.perudo.repositories.PlayerRepository;
import ru.est0y.perudo.utils.SlashCommandInteractionEventUtils;
@Slf4j
@Service("sg")
@RequiredArgsConstructor
public class StartGameCommand implements SlashCommand {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final SlashCommandInteractionEventUtils utils;
    private final MembersFilter membersFilter;
    private final GameCreator gameCreator;
    private final GameMessagingService gameMessagingService;
    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var members=utils.getVoiceChannel(event).getMembers();
        membersFilter.doFilter(members);
        var game=gameCreator.create(members);
        playerRepository.saveAll(game.getPlayers()).subscribe();
        gameRepository.save(game).subscribe();

     gameRepository.findByPlayersContaining(game.getTurnHolder()).doOnNext(n->log.info(n.toString())).subscribe();
      /*  event.getUser().openPrivateChannel().flatMap(channel->channel.sendMessage("b")).queue();
        event.getJDA().retrieveUserById(event.getMember().getIdLong()).flatMap(user -> user.openPrivateChannel().flatMap(channel->channel.sendMessage("jda"))).queue();
   */
        event.reply("Игра началась").queue(v->gameMessagingService.sendToAll(event.getJDA(),game));
    //gameMessagingService.sendToAll(event.getJDA(),game);
    }
}
