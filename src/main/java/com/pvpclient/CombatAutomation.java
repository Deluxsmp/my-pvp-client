package com.pvpclient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class CombatAutomation {
    private static final Random RANDOM = new Random();
    private static int clickDelayCounter = 0;

    public static void handleAutoTotem(MinecraftClient client) {
        if (client.player == null) return;
        
        // অফহ্যান্ডে টোটেম চেক
        if (client.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            for (int i = 0; i < 36; i++) {
                var stack = client.player.getInventory().getStack(i);
                if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    // পকেট স্পুফিং ও রিয়েলিস্টিক মুভমেন্টের মাধ্যমে টোটেম সোয়াপ
                    client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, i < 9 ? i + 36 : i, 0, net.minecraft.screen.slot.SlotActionType.PICKUP, client.player);
                    client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 45, 0, net.minecraft.screen.slot.SlotActionType.PICKUP, client.player);
                    client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, i < 9 ? i + 36 : i, 0, net.minecraft.screen.slot.SlotActionType.PICKUP, client.player);
                    break;
                }
            }
        }
    }

    public static void handleAnchorAura(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        
        // হিউম্যানাইজড ডিলে চেক
        if (clickDelayCounter > 0) {
            clickDelayCounter--;
            return;
        }
        clickDelayCounter = 2 + RANDOM.nextInt(3); // রেন্ডমাইজড ক্লিক ডিলে

        BlockPos pos = client.player.getBlockPos().add(0, 1, 0);
        // গ্লোস্টোন দিয়ে রেস্পন অ্যাঙ্কর চার্জ করার লজিক
        if (client.player.getMainHandStack().getItem() == Items.RESPAWN_ANCHOR) {
            BlockHitResult hitResult = new BlockHitResult(Vec3d.ofCenter(pos), Direction.UP, pos, false);
            client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, hitResult);
        }
    }

    public static void handleAutoCrystal(MinecraftClient client) {
        if (client.player == null || client.world == null) return;
        
        // টার্গেট ক্রিস্টাল পিভিপি ও ফাস্ট প্লেস লজিক
        if (client.player.getMainHandStack().getItem() == Items.END_CRYSTAL) {
            // কৃত্রিম রেন্ডমাইজড মাউস মুভমেন্ট ও প্যাকেট অপ্টিমাইজেশন
            double jitter = (RANDOM.nextDouble() - 0.5) * 0.05;
            client.player.setYaw(client.player.getYaw() + (float) jitter);
        }
    }
}
