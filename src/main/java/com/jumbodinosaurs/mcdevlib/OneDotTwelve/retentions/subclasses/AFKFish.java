package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.WorldHelper;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


public class AFKFish extends BindableRentention
{
    
    private transient EntityFishHook playersHook;
    private transient double hookedMotionY = -.07;
    private transient double ticksSinceClick = 0;
    
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Automates The Fishing process.";
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
            if(PlayerHelper.getPlayerController() == null)
            {
                return;
            }
            
            double tickThreshold = 1000;
            double recastTick = tickThreshold / 2;
            if(playersHook != null && !playersHook.isDead && playersHook.isInWater())
            {
                if(playersHook.motionY < hookedMotionY && ticksSinceClick > tickThreshold)
                {
                    ticksSinceClick = 0;
                    PlayerHelper.getPlayerController()
                                .processRightClick(PlayerHelper.getPlayer(),
                                                   WorldHelper.getWorld(),
                                                   EnumHand.MAIN_HAND);
                    
                }
    
                
                /*
                String output = "";
                output += "\r\n" + playersHook.motionY;
                File statsFile = DataController.checkFor(DataController.jumboModsDir, "fishyStats.csv");
                DataController.writeContents(statsFile, output, true);*/
            }
            
            if(ticksSinceClick == recastTick)
            {
                PlayerHelper.getPlayerController()
                            .processRightClick(PlayerHelper.getPlayer(), WorldHelper.getWorld(), EnumHand.MAIN_HAND);
            }
            
            if(ticksSinceClick <= tickThreshold)
            {
                ticksSinceClick++;
            }
            
        }
        
        
    }
    
    @SubscribeEvent
    public void onFish(EntityEvent event)
    {
        if(event.getEntity() instanceof EntityFishHook)
        {
            if(((EntityFishHook) event.getEntity()).getAngler() != null &&
               ((EntityFishHook) event.getEntity()).getAngler().equals(PlayerHelper.getPlayer()))
            {
                playersHook = (EntityFishHook) event.getEntity();
            }
        }
    }
    
    @SubscribeEvent
    public void onWindowLoad(GuiScreenEvent event)
    {
    
    }
    
    
}
