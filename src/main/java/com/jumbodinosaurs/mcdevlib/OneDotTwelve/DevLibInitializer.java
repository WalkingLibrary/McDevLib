package com.jumbodinosaurs.mcdevlib.OneDotTwelve;


import com.jumbodinosaurs.devlib.commands.CommandManager;
import com.jumbodinosaurs.devlib.commands.MessageResponse;
import com.jumbodinosaurs.devlib.commands.exceptions.WaveringParametersException;
import com.jumbodinosaurs.devlib.util.GeneralUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.MainMenuManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.RetentionManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.settings.OptionsManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.DevLibKeyBindManager;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.io.File;


@Mod(useMetadata = true, modid = DevLibInitializer.modid, version = DevLibInitializer.version) public class DevLibInitializer
{
    
    public static final String modid = "mcdevlib";
    public static final String version = "1.0.0";
    public static final String modDirName = "Jums Dev Lib";
    public static File modDir = GeneralUtil.checkFor(GameHelper.minecraftDir, modDirName);
    
    
    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    
    }
    
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        RetentionManager.initializeRetentions();
        OptionsManager.initializeSettings();
        DevLibKeyBindManager.registerKeyBinds();
        MainMenuManager.refreshMenu();
        CommandManager.refreshCommands();
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    
    @SubscribeEvent
    public void onChatMessage(ClientChatEvent event)
    {
        String message = event.getMessage();
        GameHelper.getInstance().ingameGUI.getChatGUI().addToSentMessages(message);
        try
        {
            MessageResponse response = CommandManager.filter(message, true);
            if(response != null)
            {
                GameHelper.sendLocalMessage(response.getMessage());
                event.setCanceled(true);
            }
            
        }
        catch(WaveringParametersException e)
        {
            GameHelper.sendLocalMessage(e.getMessage());
            event.setCanceled(true);
        }
        
    }
    
    
    @SubscribeEvent
    public void onKeyPressed(InputEvent.KeyInputEvent event)
    {
        for(Retention retention : RetentionManager.getLoadedRetentions())
        {
            if(retention instanceof BindableRentention)
            {
                
                BindableRentention bindableRentention = (BindableRentention) retention;
                if(bindableRentention.getBinding() != null && bindableRentention.getBinding().isKeyDown())
                {
                    bindableRentention.onPress();
                }
            }
        }
    }
}
