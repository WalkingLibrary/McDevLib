package com.mixin;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings.MixinsOption;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings.OptionsManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class Core implements IFMLLoadingPlugin
{
    
    
    public Core()
    {
        OptionsManager.initialiseSettings();
        if(OptionsManager.getOption(MixinsOption.class.getSimpleName()).isActive())
        {
            MixinBootstrap.init();
            Mixins.addConfiguration("mixinsDev.json");
            MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        }
    }
    
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }
    
    @Override
    public String getModContainerClass()
    {
        return null;
    }
    
    @Override
    public String getSetupClass()
    {
        return null;
    }
    
    @Override
    public void injectData(Map<String, Object> data)
    {
    }
    
    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }
}
