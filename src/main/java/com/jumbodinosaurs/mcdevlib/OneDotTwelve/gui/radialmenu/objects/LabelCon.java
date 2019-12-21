package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.ButtonAction;
import net.minecraft.util.ResourceLocation;

public class LabelCon
{
    private ResourceLocation resourceLocation;
    private String label;
    private ButtonAction action;
    
    public LabelCon(ResourceLocation resourceLocation, String label, ButtonAction action)
    {
        this.resourceLocation = resourceLocation;
        this.label = label;
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
    
    public String getLabel()
    {
        return label;
    }
    
    public void setLabel(String label)
    {
        this.label = label;
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
