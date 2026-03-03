package com.notGecko.fast_log.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import static com.notGecko.fast_log.Fast_logClient.config;

public class utils {
    public static void spawnEffects(MinecraftClient client) {
        if (client.player == null || client.world == null) return;

        Random random = new Random();
        Vec3d pos = client.player.getPos();

        // spiral
        for (int i = 0; i < 45; i++) {
            // t - corner (until 0 to 2 P), i/20.0 full circle
            double t = (i / 20.0) * Math.PI * 2;
            double r = 0.8; // radius of the spiral

            double x = Math.cos(t) * r;
            double z = Math.sin(t) * r;
            double y = (i / 20.0) * 1.8; // to player height

            // 1. MIX: magick (Enchant)
            client.world.addParticle(
                    ParticleTypes.ENCHANT,
                    pos.x + x, pos.y + y, pos.z + z,
                    0, 0.1, 0 // speed (upwards)
            );

            // 2. End Rod
            if (i % 4 == 0) {
                client.world.addParticle(
                        ParticleTypes.END_ROD,
                        pos.x + x, pos.y + y, pos.z + z,
                        random.nextGaussian() * 0.01, 0.05, random.nextGaussian() * 0.01
                );
            }
        }
    }

    public static boolean validate(MinecraftClient client) {
        String ip = client.getCurrentServerEntry() != null ? client.getCurrentServerEntry().address : "local";
        if (!config.allowedIps.contains(ip)) return false;

        if (config.checkBlindness) {
            return client.player.hasStatusEffect(StatusEffects.BLINDNESS);
        }
        return true;
    }

}
