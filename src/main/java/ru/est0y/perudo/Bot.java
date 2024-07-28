package ru.est0y.perudo;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import ru.est0y.perudo.services.buttonListeners.ButtonListenersManager;
import ru.est0y.perudo.services.commands.CommandManager;
import ru.est0y.perudo.services.commands.move.MoveCommand;
import ru.est0y.perudo.utils.CustomEventProducer;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends ListenerAdapter {
    private final CommandManager commandManager;

    private final ButtonListenersManager buttonListenersManager;

    private final MoveCommand moveCommand;

    private final CustomEventProducer customEventProducer;


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        var wordsList = Arrays.stream(event.getMessage().getContentRaw().split(" ")).toList();
        try {
            Integer.valueOf(wordsList.get(0));
            Integer.valueOf(wordsList.get(1));
        } catch (Exception e) {
            return;
        }
        var customEvent = customEventProducer.produce(event);
        moveCommand.execute(customEvent, Integer.parseInt(wordsList.get(0)), Integer.parseInt(wordsList.get(1)));
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        log.info(event.getName());
        commandManager.getCommandByName(event.getName())
                .execute(event);
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getUser().isBot()) {
            return;
        }
        buttonListenersManager.getListener(event.getComponentId()).click(event);
    }

    @SneakyThrows
    @Override
    public void onScheduledEventCreate(@Nonnull ScheduledEventCreateEvent event) {

    }
}
