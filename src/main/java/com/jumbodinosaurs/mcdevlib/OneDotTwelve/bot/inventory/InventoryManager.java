package com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.exceptions.NoContainerOpenException;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.inventory.exceptions.NoItemException;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.bot.pathfinding.util.MinecraftPoint3D;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.ItemStackHelper;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryManager
{
    
    public static int offHandSlotNumber = 45;
    
    public static void equipItem(Item itemToEquip) throws NoItemException
    {
        int itemSlotNumber = searchContainerForItemType(itemToEquip.getDefaultInstance(), getPlayerContainer());
        if(itemSlotNumber == -1)
        {
            throw new NoItemException("Player Inventory Doesn't have The Specified item.\nItem: " + itemToEquip.getUnlocalizedName());
        }
        switchItem(itemSlotNumber, getSelectedHotBarSlotConverted(), getPlayerContainer());
    }
    
    
    public static void switchItem(int slotOne, int slotTwo, Container container)
    {
        if(slotOne == slotTwo)
        {
            return;
        }
        
        boolean needExtraClick = true;
        if(ItemStackHelper.itemStacksSameItem(ItemStack.EMPTY, getItemStackAt(slotTwo, container)))
        {
            needExtraClick = false;
        }
        
        simulateSlotClick(slotOne, container);
        simulateSlotClick(slotTwo, container);
        
        if(needExtraClick)
        {
            simulateSlotClick(slotOne, container);
        }
    }
    
    public static void simulateSlotClick(int slot, Container container)
    {
        PlayerHelper.getPlayerController()
                    .windowClick(container.windowId, slot, 0, ClickType.PICKUP, PlayerHelper.getPlayer());
    }
    
    
    //searches the players invetory for the specified item and returns the slot it's in if in it
    //if the item is not in the players inventory then it returns -1
    //Note:
    //don't use player.inventory use player.inventoryContainer as it makes life easier
    public static int searchContainerForItemType(ItemStack itemStack, Container container)
    {
        
        for(int i = 0; i < container.inventorySlots.size(); i++)
        {
            
            if(ItemStackHelper.itemStacksSameItem(getItemStackAt(i, container), itemStack))
            {
                return i;
            }
        }
        
        return -1;
    }
    
    public static int searchContainerForItemType(ItemStack itemStack, Container container, int min, int max)
    {
        
        for(int i = 0; i < container.inventorySlots.size(); i++)
        {
            
            if(ItemStackHelper.itemStacksSameItem(getItemStackAt(i, container), itemStack) && min <= i && i <= max)
            {
                return i;
            }
        }
        
        return -1;
    }
    
    
    public static int getSelectedHotBarSlotConverted()
    {
        return mapPlayerInventoryToPlayerContainer(PlayerHelper.getPlayer().inventory.currentItem);
    }
    
    
    public static ItemStack getItemStackAt(int slot, Container container)
    {
        return container.inventorySlots.get(slot).getStack();
    }
    
    public static boolean subContainerHasEmptySlot(Container container)
    {
        int firstEmptySlot = searchContainerForItemType(ItemStack.EMPTY, container);
        if(firstEmptySlot == -1)
        {
            return false;
        }
        
        return firstEmptySlot <= container.getInventory().size() - 36;
    }
    
    public static int getCount(ItemStack item, Container container)
    {
        int amount = 0;
        for(int i = 0; i < container.inventorySlots.size(); i++)
        {
            ItemStack currentStack = getItemStackAt(i, container);
            if(ItemStackHelper.itemStacksSameItem(currentStack, item))
            {
                amount+= currentStack.getCount();
            }
        }
        return amount;
    }
    
    
    
    public static void detailContainer(Container container)
    {
        System.out.println("Size: " + container.getInventory().size());
        System.out.println("Window ID: " + container.windowId);
        for(int i = 0; i < container.getInventory().size(); i++)
        {
            System.out.println("Slot : " + i + " " + container.getSlot(i).getStack().getUnlocalizedName());
        }
    }
    
    public static boolean playersMainHasEmptySlot(Container container)
    {
        return searchContainerForItemType(ItemStack.EMPTY, container, 9, 44) != -1;
    }
    
    public static boolean hasEmptySlot(Container container)
    {
        return searchContainerForItemType(ItemStack.EMPTY, container) != -1;
    }
    
    public static boolean isPlayersSlot(int slot, Container container)
    {
        return slot > (container.getInventory().size() - 36);
    }
    
    
    public static int mapPlayerInventoryToPlayerContainer(int inventorySlotNumber)
    {
        if((inventorySlotNumber <= 9 || inventorySlotNumber >= 36))
        {
            //The "slots" in player.inventoryContainer map to different numbers then in player.inventory
            //This means methods like playercontroller.windowClick() try clicking the wrong slots
            //While slots 9-35 are the same
            //slots 36-45 map to 0-8
            //slots 0-8 map to 36-44
            if(inventorySlotNumber >= 36 || inventorySlotNumber <= 44)
            {
                return inventorySlotNumber + 36;
            }
            else
            {
                return inventorySlotNumber - 36;
            }
        }
        System.out.println("" + inventorySlotNumber);
        return inventorySlotNumber;
    }
    
    public static int mapPlayerContainerToExternalContainer(int inventorySlotNumber, Container container)
    {
        if(inventorySlotNumber < 9 || inventorySlotNumber > 44)
        {
            return -1;
        }
        int sizeDifference = container.getInventory().size() - getPlayerContainer().getInventory().size() + 1;
        
        return inventorySlotNumber + sizeDifference;
    }
    
    public static Container getPlayerContainer()
    {
        return PlayerHelper.getPlayer().inventoryContainer;
    }
    
    
    public synchronized static Container getCurrentlyOpenContainer() throws NoContainerOpenException
    {
        if(GameHelper.getInstance().currentScreen != null)
        {
            if(GameHelper.getInstance().currentScreen instanceof GuiContainer)
            {
                return ((GuiContainer) GameHelper.getInstance().currentScreen).inventorySlots;
            }
        }
            throw new NoContainerOpenException("No Container open");
    }
    
    
    public synchronized static void openContainer(MinecraftPoint3D wayPoint)
    {
        
        PlayerHelper.rightClick(wayPoint);
    }
    
    public synchronized static void closeCurrentContainer()
    {
        GameHelper.getInstance().displayGuiScreen(null);
    }
    
   
    
    
}
