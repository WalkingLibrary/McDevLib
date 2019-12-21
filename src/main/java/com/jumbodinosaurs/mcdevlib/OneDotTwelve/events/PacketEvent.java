package com.jumbodinosaurs.mcdevlib.OneDotTwelve.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;


public class PacketEvent extends Event
{
    private final Packet<?> packet;
    
    public PacketEvent(Packet<?> packetIn)
    {
        packet = packetIn;
    }
    
    public Packet<?> getPacket()
    {
        return packet;
    }
    
    @Cancelable public static class Send extends PacketEvent
    {
        public Send(Packet<?> packetIn)
        {
            super(packetIn);
        }
    }
    
    @Cancelable public static class Receive extends PacketEvent
    {
        public Receive(Packet<?> packetIn)
        {
            super(packetIn);
        }
        
    }
    
}
