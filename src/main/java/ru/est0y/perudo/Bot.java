package ru.est0y.perudo;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.guild.scheduledevent.ScheduledEventCreateEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;
import ru.est0y.perudo.commands.CommandManager;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bot extends ListenerAdapter {
    private final CommandManager commandManager;



    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())return;
    }
    @Override
    public void onSlashCommandInteraction( @Nonnull SlashCommandInteractionEvent event){
        if (event.getUser().isBot())return;
        log.info(event.getName());
      commandManager.getCommandByName(event.getName()).execute(event);
    }

@SneakyThrows
@Override
    public void onScheduledEventCreate(@Nonnull ScheduledEventCreateEvent event) {

}
private void t(ScheduledEventCreateEvent event){
    event.getJDA().retrieveUserById(event.getScheduledEvent().getCreatorIdLong()).queue(user -> user.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("start event").queue()));
}
}
