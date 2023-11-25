package me.wolfii.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    /**
     * Uses code from {@link BoatEntity#onPassengerLookAround}
     */
    @Inject(method = "onPassengerLookAround", at = @At("HEAD"))
    private void applyPlayerFacingOnPassengerLookAround(Entity passenger, CallbackInfo ci) {
        if (!(passenger instanceof ClientPlayerEntity)) return;
        Entity vehicle = (Entity) (Object) this;

        float f = MathHelper.wrapDegrees(vehicle.getYaw() - vehicle.getYaw());
        float g = MathHelper.clamp(f, -180f, 180f);
        passenger.prevYaw += g - f;
        passenger.setYaw(passenger.getYaw() + g - f);
        passenger.setHeadYaw(passenger.getYaw());
    }
}
