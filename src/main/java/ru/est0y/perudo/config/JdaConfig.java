package ru.est0y.perudo.config;

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

import java.util.List;
import java.util.function.BooleanSupplier;

@Configuration
@Slf4j
public class JdaConfig {
    @Bean
    public JDA jda(@Value("${app.bot.token}") String token, Bot bot) {
        var jda = JDABuilder.createDefault(token)
                .addEventListeners(bot)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        //jda.getGuilds().forEach(guild -> guild.updateCommands().addCommands())
        jda.upsertCommand("sg", "start game").setDescriptionLocalization(DiscordLocale.RUSSIAN, "начать игру")
                .setGuildOnly(true)
                //.addSubcommands(startGameSubCommands())
                .queue();
        jda.upsertCommand("move", "make move").setDescriptionLocalization(DiscordLocale.RUSSIAN, "сделать ход")
                .addOption(OptionType.INTEGER,"count","dice count",true)
                .addOption(OptionType.INTEGER,"value","dice value",true)
                .queue();
       // jda.upsertCommand("bet","make bet").queue();
        return jda;
    }

    public List<SubcommandData> startGameSubCommands() {
        return List.of(
                new SubcommandData("classic", "start a classic game"),
                new SubcommandData("ranked", "start a ranked game")
        );
    }
}
