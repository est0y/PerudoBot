package ru.est0y.perudo.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class CustomEventProducer {
    public CustomEvent produce(SlashCommandInteractionEvent event){
        return new CustomEvent() {
            @Override
            public void reply(String string) {

                event.reply(string).queue();
            }

            @Override
            public void reply(MessageCreateData messageCreateData) {
                event.reply(messageCreateData).queue();
            }

            @Override
            public User getUser() {
                return event.getUser();
            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public JDA getJDA() {
                return event.getJDA();
            }
        };
    }
    public CustomEvent produce(MessageReceivedEvent event){
        return new CustomEvent() {
            @Override
            public void reply(String string) {
                event.getMessage().reply(string).queue();
            }

            @Override
            public void reply(MessageCreateData messageCreateData) {
                event.getMessage().reply(messageCreateData).queue();
            }

            @Override
            public User getUser() {
                return event.getAuthor();
            }

            @Override
            public Map<String, String> getParams() {
                return null;
            }

            @Override
            public JDA getJDA() {
                return event.getJDA();
            }
        };
    }

}
