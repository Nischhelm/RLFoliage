package mods.betterfoliage.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import mods.betterfoliage.client.Hooks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldClient.class)
public abstract class WorldClientMixin extends World {
    protected WorldClientMixin(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    // where: WorldClient.showBarrierParticles(), right after invoking Block.randomDisplayTick
    // what: invoke BF code for every random display tick
    // why: allows us to catch random display ticks, without touching block code
    @Inject(
            method = "showBarrierParticles",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;randomDisplayTick(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)V", shift = At.Shift.AFTER)
    )
    private void rlfoliage_worldClient_showBarrierParticles(CallbackInfo ci, @Local IBlockState state, @Local(argsOnly = true) BlockPos.MutableBlockPos pos) {
        Hooks.onRandomDisplayTick(this, state, pos);
    }
}
