package com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.CPacketLoginStart;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.io.File;
import java.net.InetAddress;

public class GameHelper
{
    
    public static File minecraftDir = new File(System.getProperty("user.dir"));
    
    
    public static File initMinecraftDirectory()
    {
        File mdDir =  new File(System.getProperty("user.dir")).getParentFile();
        System.out.println(mdDir.getAbsolutePath());
        return mdDir;
    }
    
    public static void disconnect(String reason)
    {
        try
        {
            getInstance().getConnection().getNetworkManager().closeChannel(new TextComponentString(reason));
        }
        catch(Exception e)
        {
        
        }
    }
    
    
    public static boolean tryToConnect(String server)
    {
        System.out.println("Trying To Connect To: " + server);
        String serverTemp = "";
        int port = 25565;
        if(server.contains(":"))
        {
            serverTemp = server.substring(0, server.indexOf(":"));
            port = Integer.parseInt(server.substring(server.indexOf(":") + 1));
        }
        
        try
        {
            NetworkManager manager = NetworkManager.createNetworkManagerAndConnect(InetAddress.getByName(serverTemp),
                                                                                   port,
                                                                                   Minecraft.getMinecraft().gameSettings
                                                                                           .isUsingNativeTransport());
            manager.setNetHandler(new NetHandlerLoginClient(manager, Minecraft.getMinecraft(), null));
            manager.sendPacket(new C00Handshake(serverTemp, port, EnumConnectionState.LOGIN, true));
            manager.sendPacket(new CPacketLoginStart(Minecraft.getMinecraft().getSession().getProfile()));
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    public static FontRenderer getFontRender()
    {
        return Minecraft.getMinecraft().fontRenderer;
    }
    
    public static Minecraft getInstance()
    {
        return Minecraft.getMinecraft();
    }
    
    public static float getPartialTicks()
    {
        return getInstance().getRenderPartialTicks();
    }
    
    public static void sendLocalMessage(String message)
    {
        if(PlayerHelper.safeToCheck())
        {
            TextComponentString messageStyled = new TextComponentString(message);
            messageStyled.setStyle(new Style().setColor(TextFormatting.DARK_GREEN));
            Minecraft.getMinecraft().player.sendMessage(messageStyled);
        }
    }
}
