package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoBossBar extends BindableRentention implements IDisplayable
{
    
    
    public NoBossBar(KeyBinding binding)
    {
        super(binding);
    }
    
    public NoBossBar(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public NoBossBar()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Hides All Boss Bars at the top of the game screen";
    }
    
    @SubscribeEvent
    public void onScreenEvent(RenderGameOverlayEvent.BossInfo event)
    {
        if(this.isActive())
        {
            event.setCanceled(true);
        }
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return getRetentionIdentifier();
    }
    
    @Override
    public void toggle()
    {
        onPress();
    }
}
