package com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings;

import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.DevLibInitializer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.mixin.Core;
import net.minecraft.util.ResourceLocation;

import java.io.File;

public class MixinsOption extends Option
{
    private static File specialMixinsFile = GeneralUtil.checkFor(DevLibInitializer.modDir, Core.mixinsSpecialFileName);
    
    @Override
    public boolean getDefaultState()
    {
        return true;
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public void toggle()
    {
        super.toggle();
        GeneralUtil.writeContents(specialMixinsFile,"" + this.isActive(), false);
    }
    
    @Override
    public String getDisplayInfo()
    {
        return "When Active this will allow Mixins To Inject Code at Runtime (Requires Restart)\nIs Active: " + isActive();
    }
    
}
