package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ElytraFlight extends BindableRentention implements IDisplayable
{
    
    
    public ElytraFlight(KeyBinding binding)
    {
        super(binding);
    }
    
    public ElytraFlight(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public ElytraFlight()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
        if(!isActive())
        {
            Minecraft.getMinecraft().player.capabilities.isFlying = false;
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Used for Elytra Flying";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    @SubscribeEvent
    public void onTick(TickEvent event)
    {
        if(isActive())
        {
            if(PlayerHelper.safeToCheck())
            {
                PlayerHelper.getPlayer().capabilities.isFlying = true;
            }
        }
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
