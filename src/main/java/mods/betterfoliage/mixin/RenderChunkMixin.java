package mods.betterfoliage.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import mods.betterfoliage.client.Hooks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderChunk.class)
public abstract class RenderChunkMixin {
    // where: RenderChunk.rebuildChunk()
    // what: replace call to BlockRendererDispatcher.renderBlock()
    // why: allows us to perform additional rendering for each block
    @Redirect(
            method = "rebuildChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockRendererDispatcher;renderBlock(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/renderer/BufferBuilder;)Z")
    )
    private boolean rlfoliage_renderChunk_rebuildChunk(BlockRendererDispatcher instance, IBlockState iblockstate, BlockPos blockpos$mutableblockpos, IBlockAccess worldView, BufferBuilder bufferbuilder, @Local BlockRenderLayer blockrenderlayer) {
        return Hooks.renderWorldBlock(instance, iblockstate, blockpos$mutableblockpos, worldView, bufferbuilder, blockrenderlayer);
    }

    // what: invoke code to overrule result of Block.canRenderInLayer()
    // why: allows us to render transparent quads for blocks which are only on the SOLID layer
    @Redirect(
            method = "rebuildChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;canRenderInLayer(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockRenderLayer;)Z")
    )
    private boolean rlfoliage_renderChunk_rebuildChunk_noOptifine(Block instance, IBlockState state, BlockRenderLayer layer){
        return Hooks.canRenderBlockInLayer(instance, state, layer);
    }

    // what: invoke code to overrule result of Block.canRenderInLayer()
    // why: allows us to render transparent quads for blocks which are only on the SOLID layer (when optifine is present)
    @ModifyVariable(
            method = "rebuildChunk",
            at = @At("STORE"),
            name = "canRenderInLayer" //added by optifine
    )
    private boolean rlfoliage_renderChunk_rebuildChunk_optifine(boolean original, @Local Block block, @Local IBlockState state, @Local BlockRenderLayer layer){
        return Hooks.canRenderBlockInLayer(block, state, layer);
    }
}
