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
import reactor.core.scheduler.Scheduler;
import ru.est0y.perudo.services.buttonListeners.ButtonListenersManager;
import ru.est0y.perudo.services.commands.CommandManager;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends ListenerAdapter {
    private final CommandManager commandManager;
    private final ButtonListenersManager buttonListenersManager;
    private final Scheduler workPool;


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        var wordsList=Arrays.stream(event.getMessage().getContentRaw().split(" ")).toList();

        //new SlashCommandInteractionEvent()
    }

    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        if (event.getUser().isBot()) return;
        log.info(event.getName());
        commandManager.getCommandByName(event.getName())
                .execute(event)
                .publishOn(workPool).subscribe();
        //commandManager.getCommandByName(event.getName()).execute(event);
    }
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getUser().isBot()) return;
        buttonListenersManager.getListener(event.getComponentId()).click(event);
    }

    @SneakyThrows
    @Override
    public void onScheduledEventCreate(@Nonnull ScheduledEventCreateEvent event) {

    }

    private void t(ScheduledEventCreateEvent event) {
      //  event.getJDA().retrieveUserById(event.getScheduledEvent().getCreatorIdLong()).queue(user -> user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("start event").queue()));
    }
}
