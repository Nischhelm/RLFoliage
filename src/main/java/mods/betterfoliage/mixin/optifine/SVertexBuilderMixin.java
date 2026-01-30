package mods.betterfoliage.mixin.optifine;

import com.llamalad7.mixinextras.sugar.Local;
import mods.betterfoliage.client.integration.ShadersModIntegration;
import net.minecraft.block.state.IBlockState;
import net.optifine.shaders.SVertexBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SVertexBuilder.class)
public abstract class SVertexBuilderMixin {
    // where: shadersmod.client.SVertexBuilder.pushEntity()
    // what: invoke code to overrule block data
    // why: allows us to change the block ID seen by shader programs
    @ModifyArg(
            method = "pushEntity(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)V",
            at = @At(value = "INVOKE", target = "Lnet/optifine/shaders/SVertexBuilder;pushEntity(J)V"),
            remap = false
    )
    private static long rlfoliage_optifineSVertexBuilder_pushEntity(long data, @Local(argsOnly = true) IBlockState blockState) {
        return ShadersModIntegration.getBlockIdOverride(data, blockState);
    }
}
