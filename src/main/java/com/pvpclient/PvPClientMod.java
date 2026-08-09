package com.pvpclient;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class PvPClientMod implements ClientModInitializer {
    public static final String MOD_ID = "pvpclient";
    private static float hitboxSize = 0.6f;
    private static boolean anchorAuraEnabled = false;
    private static boolean autoCrystalEnabled = false;

    @Override
    public void onInitializeClient() {
        // রেজিস্টার কমান্ড
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("hotbox")
                .then(ClientCommandManager.argument("size", FloatArgumentType.floatArg(0.1f, 5.0f))
                    .executes(context -> {
                        hitboxSize = FloatArgumentType.getFloat(context, "size");
                        context.getSource().sendFeedback(Text.literal("§aHitbox size updated to: " + hitboxSize));
                        return 1;
                    })
                ));

            dispatcher.register(ClientCommandManager.literal("anchoraura")
                .executes(context -> {
                    anchorAuraEnabled = !anchorAuraEnabled;
                    context.getSource().sendFeedback(Text.literal("§aAnchor Aura: " + (anchorAuraEnabled ? "ENABLED" : "DISABLED")));
                    return 1;
                }));

            dispatcher.register(ClientCommandManager.literal("autocrystal")
                .executes(context -> {
                    autoCrystalEnabled = !autoCrystalEnabled;
                    context.getSource().sendFeedback(Text.literal("§aAuto Crystal: " + (autoCrystalEnabled ? "ENABLED" : "DISABLED")));
                    return 1;
                }));
        });

        // ক্লায়েন্ট টিক লুপ
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.world == null) return;
            
            if (anchorAuraEnabled) {
                CombatAutomation.handleAnchorAura(client);
            }
            if (autoCrystalEnabled) {
                CombatAutomation.handleAutoCrystal(client);
            }
            CombatAutomation.handleAutoTotem(client);
        });
    }

    public static float getHitboxSize() {
        return hitboxSize;
    }
}
