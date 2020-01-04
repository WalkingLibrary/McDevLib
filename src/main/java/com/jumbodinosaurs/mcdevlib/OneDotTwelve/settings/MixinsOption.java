package com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import net.minecraft.util.ResourceLocation;

public class MixinsOption extends Option
{
    
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
    public String getDisplayInfo()
    {
        return "When Active this will allow Mixins To Inject Code at Runtime (Requires Restart)\nIs Active: " + isActive();
    }
    
}
