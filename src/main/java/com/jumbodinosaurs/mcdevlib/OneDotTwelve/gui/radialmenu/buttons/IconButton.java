package com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.buttons;


import com.jumbodinosaurs.mcdevlib.OneDotTwelve.gui.radialmenu.objects.LabelCon;

public abstract class IconButton extends Button
{
    protected LabelCon labelCon;
    
    public IconButton(LabelCon labelCon)
    {
        this.labelCon = labelCon;
    }
}
