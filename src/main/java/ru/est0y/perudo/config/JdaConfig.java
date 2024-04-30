package ru.est0y.perudo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.est0y.perudo.Bot;
import ru.est0y.perudo.services.messaging.EmojiDiceService;

import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JdaConfig {

    private final EmojiDiceService emojiDiceService;

    @Bean
    public JDA jda(@Value("${app.bot.token}") String token, Bot bot) {
        var jda = JDABuilder.createDefault(token)
                .addEventListeners(bot)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        jda.upsertCommand("sg", "start game").setDescriptionLocalization(DiscordLocale.RUSSIAN, "начать игру")
                .setGuildOnly(true)
                .queue();

        var diceValueOption = new OptionData(OptionType.INTEGER, "value", "dice value")
                .setRequired(true)
                .setMinValue(1)
                .setMaxValue(6);
        jda.upsertCommand("move", "make move").setDescriptionLocalization(DiscordLocale.RUSSIAN, "сделать ход")
                .addOption(OptionType.INTEGER, "count", "dice count", true)
                .addOptions(diceValueOption)
                .queue();

        jda.upsertCommand("endgame", "end game").setDescriptionLocalization(DiscordLocale.RUSSIAN, "закончить игру")
                .queue();
        return jda;
    }

    public List<SubcommandData> startGameSubCommands() {
        new OptionData(OptionType.INTEGER, "dicevalue", "dice value")
                .setRequired(true)
                .addChoice(emojiDiceService.getAsString(1), 1)
                .addChoice(emojiDiceService.getAsString(2), 2)
                .addChoice(emojiDiceService.getAsString(3), 3)
                .addChoice(emojiDiceService.getAsString(4), 4)
                .addChoice(emojiDiceService.getAsString(5), 5)
                .addChoice(emojiDiceService.getAsString(6), 6);
        return List.of(
                new SubcommandData("classic", "start a classic game"),
                new SubcommandData("ranked", "start a ranked game")
        );
    }
}
