package com.notGecko.fast_log.mixins;

import com.notGecko.fast_log.Fast_logClient;
import com.notGecko.fast_log.util.MessageCatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    private void onMessage(Text message, CallbackInfo ci) {
        if (Fast_logClient.config == null || !Fast_logClient.config.enabled) return;

        String target = Fast_logClient.config.hideFromChat;
        if (target != null && !target.trim().isEmpty()) {
            if (message.getString().contains(target)) {
                MinecraftClient.getInstance().inGameHud.setOverlayMessage(message, false);
                ci.cancel();

                MessageCatcher.activate();
            }
        }
    }
}