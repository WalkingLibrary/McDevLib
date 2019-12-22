package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class TextDrawer
{
    
    public static void drawString(String message, int x, int y, String color) throws NumberFormatException
    {
        if(GameHelper.getFontRender() != null)
        {
            GameHelper.getFontRender().drawString(message, x, y, Integer.parseInt(color, 16));
        }
    }
    
    public static void drawStringCentered(String message, int x, int y, String color) throws NumberFormatException
    {
        if(GameHelper.getFontRender() != null)
        {
            drawString(message, x - (GameHelper.getFontRender().getStringWidth(message) / 2), y, color);
        }
    }
    
    public static void drawStringXEndPoint(String message, int x, int y, String color) throws NumberFormatException
    {
        if(GameHelper.getFontRender() != null)
        {
            drawString(message, x - GameHelper.getFontRender().getStringWidth(message), y, color);
        }
    }
    
    
}
