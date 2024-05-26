package com.albionrmtempire.service;

import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
public class DiscordBotService extends ListenerAdapter {

    @Value("${discord.bot.channel.id}")
    private String discordBotChannelId;

    private final JDA javaDiscordAPI;

    private final String BLACK_MARKET_COMMAND = "!blackmarket";

    private final String HELLO_COMMAND = "!hello";

    public DiscordBotService(@Value("${discord.bot.token}") String token) {
        javaDiscordAPI = JDABuilder.createDefault(token)
            .addEventListeners(this)
            .setActivity(Activity.playing("hmmm"))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (event.getAuthor().isBot()) return;
        if (!event.getChannel().getId().equals(discordBotChannelId)) return;

        var message = Optional.of(event.getMessage().getContentRaw());
        var messageChannel = Optional.ofNullable(javaDiscordAPI.getTextChannelById(discordBotChannelId));

        messageChannel.ifPresent(channel -> {
            try {
                var messageAuthor = event.getAuthor().getAsMention();
                switch (message.get().toLowerCase()) {
                    case BLACK_MARKET_COMMAND -> channel.sendMessage("Black Market - Items").queue();
                    case HELLO_COMMAND -> channel.sendMessage("Hello " + messageAuthor + " !" ).queue();
                    default -> channel.sendMessage(messageAuthor + ", typed an incorrect command!").queue();
                }
            } catch (Exception e) {
                log.error("An error occurred while processing the message: " + messageChannel.get(), e);
            }
        });
    }

}