package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.events.PacketEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
//https://github.com/TheAlphaEpsilon/taebooktitle
public class PacketHandler extends ChannelDuplexHandler
{
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception
    {
        boolean isPacketEventCanceled = false;
        if(msg instanceof Packet)
        {
            final PacketEvent.Receive inPacket = new PacketEvent.Receive((Packet<?>) msg);
            MinecraftForge.EVENT_BUS.post(inPacket);
            if(inPacket.isCanceled())
            {
                isPacketEventCanceled = true;
            }
            msg = inPacket.getPacket();
        }
        
        if(!isPacketEventCanceled)
        {
            super.channelRead(ctx, msg);
        }
    }
    
    public void write(final ChannelHandlerContext ctx, Object msg, final ChannelPromise promise) throws Exception
    {
        boolean shouldSendPacket = true;
        if(msg instanceof Packet)
        {
            final PacketEvent.Send outGoingPacket = new PacketEvent.Send((Packet<?>) msg);
            MinecraftForge.EVENT_BUS.post(outGoingPacket);
            if(outGoingPacket.isCanceled())
            {
                shouldSendPacket = false;
            }
            msg = outGoingPacket.getPacket();
        }
        if(shouldSendPacket)
        {
            super.write(ctx, msg, promise);
        }
    }
}
