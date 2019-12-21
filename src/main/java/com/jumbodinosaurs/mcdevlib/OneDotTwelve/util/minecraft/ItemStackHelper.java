package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackHelper
{
    public static ItemStack totem = Item.getItemById(449).getDefaultInstance();
    
    public static boolean itemStacksSameItem(ItemStack itemStack1, ItemStack itemStack2)
    {
        return itemStack1.getUnlocalizedName().equals(itemStack2.getUnlocalizedName());
    }
    
}
