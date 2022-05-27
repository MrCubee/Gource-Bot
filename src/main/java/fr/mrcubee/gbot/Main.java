package fr.mrcubee.gbot;

import fr.mrcubee.gbot.gource.Gource;
import fr.mrcubee.gbot.gource.Node;
import fr.mrcubee.gbot.gource.task.Task;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;


public class Main {

    public static final Logger LOGGER = LogManager.getRootLogger();
    private static JDA jda;
    private static Guild guild;

    private static void connectJDA() throws LoginException, InterruptedException {
        final String discordToken = System.getProperty("DISCORD_TOKEN");
        final JDABuilder jdaBuilder;

        if (discordToken == null) {
            Main.LOGGER.error("Missing discord token.");
            System.exit(22);
        }
        jdaBuilder = JDABuilder.create(Arrays.asList(
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_TYPING,
                GatewayIntent.GUILD_MESSAGE_TYPING
        ));
        jdaBuilder.enableCache(Arrays.asList(
                CacheFlag.ROLE_TAGS,
                CacheFlag.MEMBER_OVERRIDES,
                CacheFlag.EMOTE
        ));
        jdaBuilder.setToken(discordToken);
        jdaBuilder.setAutoReconnect(true);
        Main.LOGGER.info("Connecting to discord...");
        Main.jda = jdaBuilder.build();
        Main.jda.awaitReady();
    }

    public static void main(String[] args) {
        final String guildId;

        try {
            connectJDA();
        } catch (LoginException | InterruptedException exception) {
            Main.LOGGER.error("Error to connect to Discord.", exception);
            System.exit(103);
        }
        guildId = System.getProperty("GUILD_ID");
        if (guildId == null) {
            Main.LOGGER.error("Missing Guild ID.");
            Main.jda.shutdown();
            System.exit(22);
        }
        Main.guild = jda.getGuildById(guildId);
        if (Main.guild == null) {
            Main.LOGGER.error("Bad Guild ID.");
            Main.jda.shutdown();
            System.exit(22);
        }
    }

    public static JDA getJda() {
        return Main.jda;
    }

    public static Guild getGuild() {
        return Main.guild;
    }
}
