package mods.betterfoliage.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mods.betterfoliage.client.Hooks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockStateContainer.StateImplementation.class)
public abstract class BlockStateContainer_StateImplementationMixin {
    // where: BlockStateContainer$StateImplementation.getAmbientOcclusionLightValue()
    // what: invoke BF code to overrule AO transparency value
    // why: allows us to have light behave properly on non-solid log blocks
    @ModifyReturnValue(method = "getAmbientOcclusionLightValue", at = @At(value = "RETURN"))
    private float rlfoliage_blockStateContainerStateImplementation_getAmbientOcclusionLightValue(float original) {
        return Hooks.getAmbientOcclusionLightValueOverride(original, (BlockStateContainer.StateImplementation) (Object) this);
    }

    // where: BlockStateContainer$StateImplementation.useNeighborBrightness()
    // what: invoke BF code to overrule _useNeighborBrightness_
    // why: allows us to have light behave properly on non-solid log blocks
    @ModifyReturnValue(method = "useNeighborBrightness", at = @At("RETURN"))
    private boolean rlfoliage_blockStateContainerStateImplementation_useNeighborBrightness(boolean original){
        return Hooks.getUseNeighborBrightnessOverride(original, (BlockStateContainer.StateImplementation) (Object) this);
    }

    // where: BlockStateContainer$StateImplementation.doesSideBlockRendering()
    // what: invoke BF code to overrule condition
    // why: allows us to make log blocks non-solid
    @ModifyReturnValue(method = "doesSideBlockRendering", at = @At("RETURN"), remap = false)
    private boolean rlfoliage_blockStateContainerStateImplementation_doesSideBlockRendering(boolean original, IBlockAccess world, BlockPos pos, EnumFacing side){
        return Hooks.doesSideBlockRenderingOverride(original, world, pos, side);
    }

    // where: BlockStateContainer$StateImplementation.isOpaqueCube()
    // what: invoke BF code to overrule condition
    // why: allows us to make log blocks non-solid
    @ModifyReturnValue(method = "isOpaqueCube", at = @At("RETURN"))
    private boolean rlfoliage_blockStateContainerStateImplementation_isOpaqueCube(boolean original){
        return Hooks.isOpaqueCubeOverride(original, (BlockStateContainer.StateImplementation) (Object) this);
    }
}
