package com.jumbodinosaurs.mcdevlib.OneDotTwelve;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;


@Mod(useMetadata = true, modid = FMLInitializer.modid, version = FMLInitializer.version) public class FMLInitializer
{
    
    public static final String modid = "mcdevlib";
    public static final String version = "0.0.1";
    
    @Mod.EventHandler
    public void preInit(FMLInitializationEvent event)
    {
    
    }
    
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
}
