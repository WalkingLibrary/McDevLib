package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PacketHandler;
import io.netty.channel.ChannelPipeline;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

//https://github.com/TheAlphaEpsilon/taebooktitle
public class PacketHook extends Retention
{
    private static boolean firstConnection = true;
    
    @SubscribeEvent
    public void init(final FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        if(PacketHook.firstConnection)
        {
            PacketHook.firstConnection = false;
            final ChannelPipeline pipeline = event.getManager().channel().pipeline();
            pipeline.addBefore("packet_handler", "listener", new PacketHandler());
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        PacketHook.firstConnection = true;
    }
    
    
    @Override
    public String getInfoMessage()
    {
        return "Adds a netty pipeline hook to manipulate incoming and outgoing packets";
    }
}
