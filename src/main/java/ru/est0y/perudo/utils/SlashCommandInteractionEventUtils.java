package ru.est0y.perudo.utils;


import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class SlashCommandInteractionEventUtils {


    private static void accept(InteractionHook any) {
        throw new NoSuchElementException();
    }

    public Member getMember(SlashCommandInteractionEvent event) {
        return Optional.ofNullable(event.getMember()).orElseThrow();
    }

    public AudioChannelUnion getVoiceChannel(SlashCommandInteractionEvent event) {
        var voiceChannel = getVoiceState(getMember(event)).getChannel();
        if (voiceChannel == null) {
            event.reply("Войдите в голосовой чат,чтобы начать игру").queue(SlashCommandInteractionEventUtils::accept
            );
        }
        return voiceChannel;
    }

    public Guild getGuild(SlashCommandInteractionEvent event) {
        return Optional.ofNullable(event.getGuild()).orElseThrow();
    }

    private GuildVoiceState getVoiceState(Member member) {
        return Optional.ofNullable(member.getVoiceState()).orElseThrow();
    }
}
