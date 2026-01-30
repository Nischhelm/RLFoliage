package mods.betterfoliage.loader;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class BetterFoliageLoader implements IFMLLoadingPlugin {
    public BetterFoliageLoader() {
        MixinBootstrap.init();
        MixinExtrasBootstrap.init();

        Mixins.addConfiguration("mixins.rlfoliage.vanilla.json");
        Mixins.addConfiguration("mixins.rlfoliage.optifine.json");
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{BetterFoliageTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
