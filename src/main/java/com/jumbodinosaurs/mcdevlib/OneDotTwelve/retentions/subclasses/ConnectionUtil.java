package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.Retention;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ConnectionUtil extends Retention
{
    private transient static String ip;
    private transient static int port;
    
    public static String getIp()
    {
        return ip;
    }
    
    public static int getPort()
    {
        return port;
    }
    
    @SubscribeEvent
    public void init(final FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        System.out.println(event.getManager().getRemoteAddress().toString());
        String connectionInfo = event.getManager().getRemoteAddress().toString();
        String[] firstSplit = connectionInfo.split(":");
        ip = "Single Player";
        if(firstSplit.length == 2)
        {
            ip = firstSplit[0];
            ip = ip.replaceAll("/", " ") + " ";
            ip = ip.split(" ")[0];
            port = Integer.parseInt(firstSplit[1]);
            System.out.println("Ip: " + ip);
            System.out.println("Port: " + port);
    
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Grabs The connection info for easy access";
    }
}
