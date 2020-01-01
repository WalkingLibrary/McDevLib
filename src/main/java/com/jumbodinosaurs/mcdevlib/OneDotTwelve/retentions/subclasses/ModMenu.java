package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.MainMenuManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.settings.KeyBinding;

public class ModMenu extends BindableRentention
{
    public ModMenu(KeyBinding binding)
    {
        super(binding);
    }
    
    public ModMenu(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public ModMenu()
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
        MainMenuManager.initMenu();
        /*
        if(MainMenuManager.getMainMenu() == null)
        {
            MainMenuManager.initMenu();
        }
        */
        GameHelper.getInstance().displayGuiScreen(MainMenuManager.getMainMenu());
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Displays The Mods GUI";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
}
