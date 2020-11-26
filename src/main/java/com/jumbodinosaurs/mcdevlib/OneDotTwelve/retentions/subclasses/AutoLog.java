package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.InventoryManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.ItemStackHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLog extends BindableRentention
{
    public int threshold = 15;
    //When true the Retention will not logout until you no longer have and totems and your health is below the threshold
    //else you will log when ever below the threshold
    public boolean useTotems;
    
    @Override
    public String getInfoMessage()
    {
        return "With luck when the player is low on health you will automatically log out before death. (Threshold " +
               "Toggleable).";
    }
    
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
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
    }
    
    @SubscribeEvent
    public void onTick(TickEvent event)
    {
        if(isActive())
        {
            if(PlayerHelper.safeToCheck() && PlayerHelper.getHealth() < threshold)
            {
                if(useTotems)
                {
                    
                    if(!InventoryManager.getItemStackAt(InventoryManager.offHandSlotNumber,
                                                        InventoryManager.getPlayerContainer())
                                        .equals(ItemStackHelper.totem))
                    {
                        GameHelper.disconnect("Auto Logged Health at: " +
                                              PlayerHelper.getHealth() +
                                              " with no Totem" +
                                              " in the off hand");
                    }
                }
                else
                {
                    this.active = false;
                    GameHelper.disconnect("Auto Logged Health at: " + PlayerHelper.getHealth());
                }
            }
        }
    }
    
    
}
