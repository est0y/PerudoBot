package ru.est0y.perudo.services.commands.startGame;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.est0y.perudo.repositories.GameRepository;
import ru.est0y.perudo.services.UserServiceImpl;
import ru.est0y.perudo.services.commands.SlashCommand;
import ru.est0y.perudo.services.gameCreation.ClassicGameCreator;
import ru.est0y.perudo.services.messaging.GameStateMessageCreator;
import ru.est0y.perudo.services.messaging.MessageSender;
import ru.est0y.perudo.utils.SlashCommandInteractionEventUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service("sg")
@RequiredArgsConstructor
public class StartGameCommand implements SlashCommand {
    private final GameRepository gameRepository;

    private final SlashCommandInteractionEventUtils utils;

    private final MembersFilter membersFilter;

    private final ClassicGameCreator gameCreator;

    private final GameStateMessageCreator gameStateMessageCreator;

    private final MessageSender messageSender;

    private final UserServiceImpl userService;


    @Transactional
    @Override
    public void execute(SlashCommandInteractionEvent event) {

        var members = utils.getVoiceChannel(event).getMembers().stream().filter(m -> !m.getUser().isBot()).toList();
        membersFilter.doFilter(members);
        try {
            userService.updateOrSave(members.stream().map(ISnowflake::getIdLong).toList());
        } catch (Exception e) {
            event.reply("Кто-то из игроков уже в игре").queue();
            return;
        }
        var game = gameCreator.create(members);
        game = gameRepository.save(game);
        var messages = gameStateMessageCreator.createPersonalMessage(game);
        event.reply("Игра началась").queue(v -> {
            //todo мб исправить
            v.deleteOriginal().queueAfter(5, TimeUnit.SECONDS);
            messageSender.send(event.getJDA(), messages);
        });
    }


}
