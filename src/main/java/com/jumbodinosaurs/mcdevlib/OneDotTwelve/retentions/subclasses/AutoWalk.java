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

public class AutoWalk extends BindableRentention implements IDisplayable
{
    
    public AutoWalk(KeyBinding binding)
    {
        super(binding);
    }
    
    public AutoWalk(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public AutoWalk()
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
        if(!active)
        {
            if(PlayerHelper.safeToCheck())
            {
                Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = false;
                //PlayerHelper.getPlayer().motionX = 0;
            }
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Automates The Walking process.";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    @SubscribeEvent
    public void onTick(TickEvent event)
    {
        if(this.isActive())
        {
            if(PlayerHelper.safeToCheck())
            {
                //PlayerHelper.getPlayer().motionX = -.065;
            }
            Minecraft.getMinecraft().gameSettings.keyBindForward.pressed = true;
        }
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.movementIcon;
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
