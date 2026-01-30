package mods.betterfoliage.mixin;

import mods.betterfoliage.client.Hooks;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    // where: ModelLoader.setupModelRegistry(), right before the textures are loaded
    // what: invoke handler code with ModelLoader instance
    // why: allows us to iterate the unbaked models in ModelLoader in time to register textures
    @Inject(
            method = "setupModelRegistry",
            at = @At(value = "INVOKE", target = "Ljava/util/Set;addAll(Ljava/util/Collection;)Z", shift = At.Shift.AFTER)
    )
    private void rlfoliage_modelLoader_setupModelRegistry(CallbackInfoReturnable<IRegistry<ModelResourceLocation, IBakedModel>> cir) {
        Hooks.onAfterLoadModelDefinitions((ModelLoader) (Object) this);
    }
}
