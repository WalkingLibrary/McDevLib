package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.mainmenu;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons.radial.RadialButton;

public abstract class RadialMainMenuButton
{
    private int priority = Integer.MIN_VALUE;
    
    public abstract RadialButton getRadialButton();
    
    public String getCategory()
    {
        return getRadialButton().getLabelCon().getLabel();
    }
    
    public abstract int getDefaultPriority();
    
    public int getPriority()
    {
        return priority;
    }
    
    public void setPriority(int priority)
    {
        this.priority = priority;
    }
}
