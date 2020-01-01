package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions;

import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.util.TextDrawer;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.GameHelper;
import net.minecraft.client.settings.KeyBinding;


public abstract class TextRenderingRetentions extends BindableRentention
{
    protected int stringSpacing = 10;
    
    private String defaultColor = "faf3f2";
    
    //TODO
    /*Write method for writing string so that if a bad color value is set it will auto fixInitialization and tell player*/
    
    
    public static String color;
    
    public TextRenderingRetentions(KeyBinding binding)
    {
        super(binding);
    }
    
    public TextRenderingRetentions(boolean active,
                                   KeyBinding binding)
    {
        super(active, binding);
    }
    
    public TextRenderingRetentions()
    {
    }
    
  
    @Override
    public void onPress()
    {
        defaultPress();
    }
    
    
    public void drawString(int x, int y, String message)
    {
        try
        {
            if(color == null)
            {
               color = this.defaultColor;
            }
            TextDrawer.drawString(message,
                                  x,
                                  y,
                                  color);
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            onDrawFailure();
        }
    }
    
    
    public void drawStringXEndPoint(int x, int y, String message)
    {
        try
        {
            if(color == null)
            {
                color = this.defaultColor;
            }
            TextDrawer.drawStringXEndPoint(message,
                                           x,
                                           y,
                                           color);
        }
        catch(NumberFormatException e)
        {
            e.printStackTrace();
            onDrawFailure();
        }
    }
    
    
    public void onDrawFailure()
    {
        GameHelper.sendLocalMessage("The color set for " + getRetentionIdentifier() + " caused problems. Setting " +
                                                "it's" +
                                                " color to the default Color");
        color = this.defaultColor;
    }
    
    public static String getColor()
    {
        return color;
    }
}
