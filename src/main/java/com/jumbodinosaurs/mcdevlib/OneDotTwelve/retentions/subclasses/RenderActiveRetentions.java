package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.TextRenderingRetentions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class RenderActiveRetentions extends TextRenderingRetentions implements IDisplayable
{
    
    
    public RenderActiveRetentions(KeyBinding binding)
    {
        super(binding);
    }
    
    public RenderActiveRetentions(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public RenderActiveRetentions()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Text event)
    {
        if(this.isActive())
        {
            //Give some buffer room so that it does go off screen
            int count = 1;
            for(Retention retention : RetentionManager.getLoadedRetentions())
            {
                
                ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
                if(retention.isActive())
                {
                    drawStringXEndPoint(res.getScaledWidth(),
                                        res.getScaledHeight() - (count * this.stringSpacing),
                                        retention.getRetentionIdentifier());
                    
                    count++;
                }
            }
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "When active the mod will render all mods active.";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return "Active Mods";
    }
    
    @Override
    public void toggle()
    {
        onPress();
    }
}
