package ru.est0y.perudo.services.commands;


import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import reactor.core.publisher.Mono;

public interface SlashCommand {
    Mono<Void> execute(SlashCommandInteractionEvent event);
}
