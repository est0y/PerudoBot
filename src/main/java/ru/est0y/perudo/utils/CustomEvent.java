package ru.est0y.perudo.utils;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;

import java.util.Map;

public interface CustomEvent {
    void reply(String string);

    void reply(MessageCreateData messageCreateData);

    User getUser();

    Map<String, String> getParams();

    JDA getJDA();
}
