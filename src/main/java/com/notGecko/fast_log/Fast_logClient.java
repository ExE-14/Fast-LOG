package com.notGecko.fast_log;

import com.notGecko.fast_log.client.config.FlogConfig;
import com.notGecko.fast_log.util.MessageCatcher;
import com.notGecko.fast_log.util.utils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;

import net.minecraft.sound.SoundEvents;

public class Fast_logClient implements ClientModInitializer {
    public static FlogConfig config;
    private int ticksPassed = 0;
    private boolean hasSentThisSession = false; // send only once per session, reset on world join

    @Override
    public void onInitializeClient() {
        AutoConfig.register(FlogConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(FlogConfig.class).getConfig();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            this.hasSentThisSession = false;
            MessageCatcher.reset();

            ticksPassed = 0;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || !config.enabled || this.hasSentThisSession || MessageCatcher.isMessageCatched()) return;

            ticksPassed++;
            if (ticksPassed < config.delayTicks) return;

            if (utils.validate(client)) {
                if (client.getNetworkHandler() != null && !MessageCatcher.isMessageCatched()) {
                    client.getNetworkHandler().sendChatCommand(config.commandToSend);
                    this.hasSentThisSession = true;

                    utils.spawnEffects(client);
                    client.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.6F, 0.6F);
                }
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("flog")
                    .executes(context -> {
                        MinecraftClient client = context.getSource().getClient();
                        client.send(() -> client.setScreen(AutoConfig.getConfigScreen(FlogConfig.class, client.currentScreen).get()));
                        return 1;
                    })
            );
        });
    }
}