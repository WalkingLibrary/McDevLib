package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.InventoryManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.ItemStackHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoTotem extends BindableRentention
{
    
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
        return "When active the retention will automatically put a totem into the off hand if there isn't on there.";
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
            if(PlayerHelper.safeToCheck() &&
               !ItemStackHelper.itemStacksSameItem(InventoryManager.getItemStackAt(InventoryManager.offHandSlotNumber,
                                                                                   InventoryManager.getPlayerContainer()),
                                                   ItemStackHelper.totem))
            {
                
                int slotTotem = InventoryManager.searchContainerForItemType(ItemStackHelper.totem,
                                                                            InventoryManager.getPlayerContainer());
                //not true programing inventory starts at 1
                
                if(slotTotem > 0)
                {
                    
                    InventoryManager.switchItem(slotTotem,
                                                InventoryManager.offHandSlotNumber,
                                                InventoryManager.getPlayerContainer());
                }
                
            }
        }
    }
}
