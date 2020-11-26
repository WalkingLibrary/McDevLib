package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;

import com.jumbodinosaurs.devlib.util.objects.ClientTimer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.events.PacketEvent;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.BindableRentention;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class AutoReconnect extends BindableRentention
{
    
    private transient String connection;
    private transient ClientTimer reconnectTryTimer = new ClientTimer(5001, new ComponentsListener());
    
    public AutoReconnect(KeyBinding binding)
    {
        super(binding);
    }
    
    public AutoReconnect(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public AutoReconnect()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    
    @SubscribeEvent
    public void onGuiChange(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if(event.getGui() instanceof GuiDisconnected)
        {
            System.out.println("Disconnected Starting Reconnect Process");
            reconnectTryTimer.start();
        }
    }
    
    @SubscribeEvent
    public void onReceive(PacketEvent.Send event)
    {
        if(event.getPacket() instanceof C00Handshake)
        {
            C00Handshake packet = ((C00Handshake) event.getPacket());
            try
            {
                Field fieldIp = packet.getClass().getDeclaredField("ip");
                Field fieldPort = packet.getClass().getDeclaredField("port");
                
                fieldIp.setAccessible(true);
                String ip = (String) fieldIp.get(packet);
                
                fieldPort.setAccessible(true);
                int port = (int) fieldPort.get(packet);
                connection = ip + ":" + port;
                System.out.println("Server To Reconnect To set to " + connection);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    @Override
    public String getInfoMessage()
    {
        return "When Active the Retention will automatically rejoin the last server joined.";
    }
    
    @Override
    public void toggleActive()
    {
        super.toggleActive();
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    private class ComponentsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(isActive() && e.getSource().equals(reconnectTryTimer))
            {
                
                if(connection != null && !connection.equals("singleplayer"))
                {
                    System.out.println("Trying to connect to " + connection);
                    boolean connectionEstablished = GameHelper.tryToConnect(connection);
                    if(connectionEstablished)
                    {
                        System.out.println("Connected to " + connection);
                        reconnectTryTimer.stop();
                    }
                }
                else
                {
                    reconnectTryTimer.stop();
                }
            }
        }
    }
}
