package ru.est0y.perudo.services.commands;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

@Service("help")
@RequiredArgsConstructor
public class HelpCommand implements SlashCommand {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Команда **/sg** начинает игру с игроками,которые находятся" +
                "в голосовом чате вместе с инициатором команды **/sg**.\n" +
                "Чтобы сделать ход напишите 2 числа.Первое число количество кубов,а второе число" +
                "это номинал кости." +
                "Например: 2 6 - это две шестерки").queue();

    }
}
