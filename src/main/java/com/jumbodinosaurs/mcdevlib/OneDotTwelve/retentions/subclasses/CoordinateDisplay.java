package com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.subclasses;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.ResourceLocationUtil;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.interfaces.IDisplayable;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.retentions.TextRenderingRetentions;
import com.jumbodinosaurs.mcdevlib.OneDotTwelve.util.minecraft.PlayerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CoordinateDisplay extends TextRenderingRetentions implements IDisplayable
{
    
    
    public CoordinateDisplay(KeyBinding binding)
    {
        super(binding);
    }
    
    public CoordinateDisplay(boolean active, KeyBinding binding)
    {
        super(active, binding);
    }
    
    public CoordinateDisplay()
    {
    }
    
    @Override
    public String getModID()
    {
        return "DevLib";
    }
    
    
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Text event)
    {
        if(this.isActive())
        {
            if(Minecraft.getMinecraft() != null && Minecraft.getMinecraft().player != null)
            {
                String coordinateDisplay = "Coordinates " + PlayerHelper.getCoordinates();
                String[] coordinates = coordinateDisplay.split(" ");
                
                int count = 0;
                for(String coordinate : coordinates)
                {
                    drawString(0, 0 + (count * this.stringSpacing), coordinate);
                    count++;
                }
            }
        }
    }
    
    @Override
    public String getInfoMessage()
    {
        return "Shows the players current coordinates.";
    }
    
    @Override
    public String getRetentionIdentifier()
    {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public ResourceLocation getIcon()
    {
        return ResourceLocationUtil.clairvoyanceIcon;
    }
    
    @Override
    public String getLabel()
    {
        return "CoordDisp.";
    }
    
    @Override
    public void toggle()
    {
        onPress();
    }
}
