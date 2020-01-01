package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.util;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import net.minecraft.util.ResourceLocation;

public class LabelCon extends Label
{
    private ResourceLocation resourceLocation;
    private ButtonAction action;
    
    
    public LabelCon(String label, ResourceLocation resourceLocation, ButtonAction action)
    {
        super(label);
        this.resourceLocation = resourceLocation;
        this.action = action;
    }
    
   
    public ResourceLocation getResourceLocation()
    {
        return resourceLocation;
    }
    
    public void setResourceLocation(ResourceLocation resourceLocation)
    {
        this.resourceLocation = resourceLocation;
    }
    
    
    public ButtonAction getAction()
    {
        return action;
    }
    
    public void setAction(ButtonAction action)
    {
        this.action = action;
    }
}
