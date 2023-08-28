package ru.est0y.perudo.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
@RequiredArgsConstructor
public class CommandManager {
    private final Map<String, SlashCommand> commandsMap;

    public SlashCommand getCommandByName(String name) {
        return commandsMap.get(name);
    }
}
