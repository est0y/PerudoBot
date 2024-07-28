package ru.est0y.perudo.utils;


import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SlashCommandInteractionEventUtils {
    public Member getMember(SlashCommandInteractionEvent event) {
        return Optional.ofNullable(event.getMember()).orElseThrow();
    }

    public AudioChannelUnion getVoiceChannel(SlashCommandInteractionEvent event) {
        return Optional.ofNullable(
                getVoiceState(
                        getMember(event)
                ).getChannel()
        ).orElseThrow();
    }

    public Guild getGuild(SlashCommandInteractionEvent event) {
        return Optional.ofNullable(event.getGuild()).orElseThrow();
    }

    private GuildVoiceState getVoiceState(Member member) {
        return Optional.ofNullable(member.getVoiceState()).orElseThrow();
    }
}
