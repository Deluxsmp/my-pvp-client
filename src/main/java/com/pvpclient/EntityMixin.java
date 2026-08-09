package com.pvpclient.mixin;

import com.pvpclient.PvPClientMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract Box getBoundingBox();

    @Inject(method = "getTargetingMargin", at = @At("HEAD"), cancellable = true)
    private void onGetTargetingMargin(CallbackInfoReturnable<Float> cir) {
        // হিটবক্স সাইজ রিসাইজার মডিউল
        cir.setReturnValue(PvPClientMod.getHitboxSize());
    }
}
